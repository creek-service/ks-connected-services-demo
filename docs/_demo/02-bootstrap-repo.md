---
title: Bootstrap a new aggregate
permalink: /bootstrap
description: Create a copy of the first demo, if needed, on which to work
layout: single
---

This tutorial builds on the [Basic Kafka Streams demo](/basic-kafka-streams-demo/). 
If you have completed the previous demo you can use that repository for working through this tutorial: 
skip to the [nex step]({{ "/add-service" | relative_url }}).
Otherwise, follow the steps below to create your own copy of the completed tutorial:

## Create your own copy of the previous demo

1. Click [<i class="fab fa-fw fa-github"/>&nbsp; Use this template ][tempNew]{: .btn .btn--success}{:target="_blank"} to create a new repository,
   and fill in the details:
   {% include figure image_path="/assets/images/creek-create-new-aggregate.png" alt="Create new aggregate repo" %}
 
2. When GitHub creates the new repo, a [boostrap workflow <i class="fas fa-external-link-alt"></i>][bootstrapWorkflow]{:target="_blank"} will run to customise the new repository.
   Wait for this workflow to complete in the _Actions_ tab:
   {% include figure image_path="/assets/images/creek-repo-bootstrap.png" alt="Wait for boostrap workflow" %}

3. [Clone the new repository <i class="fas fa-external-link-alt"></i>][cloneRepo]{:target="_blank"} locally.
4. Finish the initialisation of the repository by running the `clean_up.sh` script from the root of the repository.

   ```
   ./.creek/clean_up.sh
   ```

   The clean-up script will finish off the customisation of the new repository, removing now redundant workflows, 
   scripts and code.

5. Commit the changes back to the GitHub
   ```
   git add -A
   git commit -m "clean_up script"
   git push
   ```

The repository is now ready for the second service to be added, which will be covered in the next step.

[tempOnGH]: https://github.com/creek-service/basic-kafka-streams-demo
[tempNew]: https://github.com/creek-service/basic-kafka-streams-demo/generate
[bootstrapWorkflow]: https://github.com/creek-service/basic-kafka-streams-demo/blob/main/.github/workflows/bootstrap.yml
[cloneRepo]: https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository