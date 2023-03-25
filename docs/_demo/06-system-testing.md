---
title: Writing system tests
permalink: /system-testing
description: Learn how to write system tests covering the combined functionality of multiple microservice running in Docker containers
layout: single
toc: true
---

With the production code of the new service complete, the next step is to add some tests.

Unit tests are useful to a point, but ideally there should be tests that cover the combined functionality of 
all the services in an aggregate, ensuring they provide the required business functionality.

Creek [system-tests][systemTests] enable black-box testing of an aggregate's combined services.
Services are run, locally, in Docker containers. Test inputs and expected
outputs are defined in YAML file, and Creek does the rest.

The service Docker images used in the system tests are the very same images that will be deployed through to Production.
By testing the actual Docker images, and assuming good test coverage, confidence can be very high that the services does what's required.

**Note:** We'd recommend familiarising yourself with the [previous tutorials page on writing
system tests][basicTutorialSysTest], as this tutorial assumes knowledge of the basics.
{: .notice--warning}

## Testing multiple services

Extending the existing system tests to include the new filter service is relatively simple. 
First, define new input tweets that include occurrences of Twitter handles from presidents of the USA, 
second, define the expected filtered output in the `twitter.handle.usage.presidents` topic, and finally
wire everything together in the test suite.

**ProTip:** Extending the existing test suite, rather than adding a new test suite, will result in faster test execution,
as each test suite adds the overhead of starting and stopping all the Docker containers.
So, having fewer test suites, with more tests per suite, will execute quicker than more suites,
with fewer tests per suite.
{: .notice--info}

### Define more test inputs

One approach would be to add new records to the existing `twitter.tweet.text.yml` input file.
A potentially more flexible approach, especially if the existing input file was re-used in several test cases already,
would be to create a new input file containing only the USA president tweets. This allows finer grained control of
which test cases the new input is added to.

Create a file at `system-tests/src/system-test/example-suite/inputs/tweets-with-presidents.yml` with the following content:

{% highlight yaml %}
{% include_snippet all from ../system-tests/src/system-test/example-suite/inputs/tweets-with-presidents.yml %}
{% endhighlight %}

This new input file adds a couple of new tweets to feed into the system. These include occurrences of _all_ the presidents Twitter
handles in the hardcoded list of our service. With the existing test data in `twitter.tweet.text.yml` containing occurrences
of Twitter handles from other, none presidential, users, this gives us good test coverage of records that are both
expected to pass through and be filtered out by the filter service.

### Define more expected outputs

Next, define the expected output of the `handle-occurrence-filtering-service` in the `twitter.handle.usage.presidents` topic.

Create a file at `system-tests/src/system-test/example-suite/expectations/twitter.handle.usage.presidents.yml` with the following content:

{% highlight yaml %}
{% include_snippet all from ../system-tests/src/system-test/example-suite/expectations/twitter.handle.usage.presidents.yml %}
{% endhighlight %}

This file defines the expected output of our new filter service, given the _existing_ input defined in the `twitter.tweet.text.yml`
input file and the new `tweets-with-presidents.yml` file.

### Update the existing test suite file

Update the existing `system-tests/src/system-test/example-suite/suite.yml` file to match the following content:

{% highlight yaml %}
{% include_snippet first-attempt from ../system-tests/src/system-test/example-suite/suite.yml %}
{% endhighlight %}

There are the following changes in the above file:

1. Add the new `handle-occurrence-filtering-service`, so that it's started as part of the suite.
   
   **Note:** The order of services in the suite is important, as it dictates the order the services are started.
   <br><br>If the order was reversed, the new service would fail to start, as its input topic is only created 
   during the startup of the `handle-occurrence-service`, so wouldn't exist yet.
   {: .notice--warning}

   **ProTip:** To start multiple instances of a service, add the service name to the list multiple times.
   {: .notice--info}
2. Include the tweets in the `tweets-with-presidents.yml` file in the test inputs
3. Include the expected filtered output defined in the `twitter.handle.usage.presidents.yml` file.

## Running the system tests

The system tests can be executed with the following Gradle command:

```
./gradlew systemTest 
```

When executed, the system tests should result in a test failure that looks something like this:

```
basic suite:test 1: Additional records were produced.
Unmatched records: [
        ConsumedRecord{record=ConsumerRecord(topic = twitter.handle.usage, partition = 2, leaderEpoch = 0, offset = 3, CreateTime = 1678453297029, serialized key size = 9, serialized value size = 4, headers = RecordHeaders(headers = [], isReadOnly = false), key = [B@6d293993, value = [B@475f5672), deserializedKey=@JoeBiden, deserializedValue=1},
        ConsumedRecord{record=ConsumerRecord(topic = twitter.handle.usage, partition = 2, leaderEpoch = 0, offset = 4, CreateTime = 1678453297029, serialized key size = 9, serialized value size = 4, headers = RecordHeaders(headers = [], isReadOnly = false), key = [B@616a06e3, value = [B@42297bdf), deserializedKey=@JoeBiden, deserializedValue=1},
        ConsumedRecord{record=ConsumerRecord(topic = twitter.handle.usage, partition = 3, leaderEpoch = 0, offset = 1, CreateTime = 1678453297029, serialized key size = 16, serialized value size = 4, headers = RecordHeaders(headers = [], isReadOnly = false), key = [B@6a55594b, value = [B@632b305d), deserializedKey=@realDonaldTrump, deserializedValue=1},
        ConsumedRecord{record=ConsumerRecord(topic = twitter.handle.usage, partition = 3, leaderEpoch = 0, offset = 2, CreateTime = 1678453297029, serialized key size = 12, serialized value size = 4, headers = RecordHeaders(headers = [], isReadOnly = false), key = [B@44598ef7, value = [B@57fdb8a4), deserializedKey=@BarackObama, deserializedValue=1},
        ConsumedRecord{record=ConsumerRecord(topic = twitter.handle.usage, partition = 4, leaderEpoch = 0, offset = 0, CreateTime = 1678453297029, serialized key size = 6, serialized value size = 4, headers = RecordHeaders(headers = [], isReadOnly = false), key = [B@195113de, value = [B@3ebc955b), deserializedKey=@POTUS, deserializedValue=2}
]
```

Why did it fail? Reading the error message, the suite failed because `Additional records were produced`. 
Looking at the rows in the `Unmatched records`, it seems all these records are in the `twitter.handle.usage` topic. 
Scrolling to the right shows the `deserializedKey`, and in each case it is the Twitter handle of a US president.

The suite is failing because, although the suite has been updated to define the expected output in the 
`twitter.handle.usage.presidents` topic, there are also new records output by the `handle-occurrence-service` 
to the `twitter.handle.usage` topic, due to the new `tweets-with-presidents.yml` input.

There is [planned work <i class="fas fa-external-link-alt"></i>][additionalRecHandlingIssue]{:target="_blank"} to give test suites and expectations control over
additional record handling, allowing them to fail or simply ignore additional records.
This will likely be implementing using the [`creek/kafka-options` <i class="fas fa-external-link-alt"></i>][kafkaOptions]{:target="_blank"} type.
Until then, the expectations must include all records.
{: .notice}

### Fixing the expected records

To fix the suite, expectations must be added to matches all the expected output.

As the functionality of the `handle-occurrence-service` has already been sufficiently tested by the existing test data,
there isn't much value if defining the expected keys and values of these additional records.

Until the Kafka test extension supporting [control of additional records <i class="fas fa-external-link-alt"></i>][additionalRecHandlingIssue]{:target="_blank"},
one approach for effectively ignoring the additional records is to add empty entries in the expected records list.
Expected records that don't define a key or value mean the contents of the records key or value isn't checked. 
Though, the test would still fail if the data failed to deserialize.

As before, these expectations could be added to the existing file, but defining then in a new file improves fine-grained
control.

Create a file at `system-tests/src/system-test/example-suite/expectations/twitter.handle.usage-any-five-records.yml` 
with the following content:

{% highlight yaml %}
{% include_snippet all from ../system-tests/src/system-test/example-suite/expectations/twitter.handle.usage-any-five-records.yml %}
{% endhighlight %}

This adds expectations for 5 more records to be output to the `twitter.handle.usage` topic. 5 records with _any valid key and value_. 

Include this new expectation file in the suite:

{% highlight yaml %}
{% include_snippet first-attempt from ../system-tests/src/system-test/example-suite/suite.yml %}
{% include_snippet any-five from ../system-tests/src/system-test/example-suite/suite.yml %}
{% endhighlight %}

Run the system tests again:

```
./gradlew systemTest 
```

...and they'll pass.

This completes the tutorial. See the next page for suggested next steps.

[additionalRecHandlingIssue]: https://github.com/creek-service/creek-kafka/issues/236
[systemTests]:https://github.com/creek-service/creek-system-test
[kafkaOptions]: https://www.creekservice.org/creek-kafka/#option-model-extensions
[basicTutorialSysTest]: {{ site.url | append: "/basic-kafka-streams-demo/system-testing" }}
[todo]: switch about links to proper creekservice.org links once each repo publishes docs. 


