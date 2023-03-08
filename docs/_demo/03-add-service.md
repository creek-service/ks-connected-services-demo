---
title: Adding a second microservice
permalink: /add-service
description: Learn how to use the Creek aggregate template to quickly add a second microservices.
layout: single
---

We'll use the same process as in previous demos to add a new service to our existing aggregate repository:

1. Go to the `Actions` tab of the new repository on GitHub.
2. Select `Add service module` from the list of available workflows on the left.
   {% include figure image_path="/assets/images/creek-add-service-workflow.png" alt="Add new service module workflow" %}
3. Click the `Run workflow ▾` button and enter the service name as `handle-occurrence-filtering-service`: 
   {% include figure image_path="/assets/images/creek-add-service.png" alt="Add new service" %}

   **Note:** Service names must be lowercase. Only alphanumerics and dashes are supported.
   {: .notice--warning}

   **ProTip:** End your service names with `-service` to make it clear the module contains a microservice.
   {: .notice--info}
4. Click the [Run workflow](){: .btn .btn--small .btn--disabled .btn--success} button below the service name.

This will kick off a workflow that adds the new module, containing the boilerplate code for a new service,
though you may need to refresh the web page to view it. 

{% include figure image_path="/assets/images/creek-add-service-workflow-running.png" alt="Running workflow" %}

Wait for the workflow to complete and pull down the changes to your local machine by running:

```shell
git pull
```
