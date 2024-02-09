# The Hibernation database

## What it is
This is a Java application that offers a simple interface to a database containing a table of animals that hibernate.
It can be called to create new animal entries, and modify or delete existing ones.

## How it works
The application uses the Spring Boot framework to provide REST functionality as well as database connectivity.
Internally Spring Boot uses the framework _Hibernate_ to do this. (Yes, I made this entire thing for that joke. You're welcome.)

The programme is built to run in what is called a Docker Container,
which is a small virtual machine containing only what is necessary for the programme to run.
It connects to a PostgreSQL database which can be hosted anywhere but is most easily run as another Docker container.
(See the deployment.yaml file for an example).

## Running it locally
For the duration of the contest I'll host the backend myself. You do not need to run this software manually to experience it working.
You only need to run the [provided frontend](https://github.com/JafethvE/ExilianWinterCreativeCompetition2023.Frontend).

If you do wish to run it yourself you need to download and install Docker Desktop [from here](https://www.docker.com/products/docker-desktop).

Download this repository, either through the github web interface or by cloning it with Git.
Once it is installed and running you can open a terminal.
Navigate to where you have extracted/cloned the project and run the command `docker-compose -f deployment.yaml up`
This wil start two Docker containers, one with a PostgreSQL database and one with this REST application.
You can then use either the [provided frontend](https://github.com/JafethvE/ExilianWinterCreativeCompetition2023.Frontend)
or, if you know what you're doing, any http client (I suggest [Postman](https://www.postman.com/)) to send commands to the application.
The url at that point will be http://localhost: followed by the port specified in the deployment.yaml file (default 1001).

## Disclaimers
This software is provided "as-is" and I make no guarantees as to the quality or security of it.
I have taken basic measures to prevent SQL and script injection and Spring does a lot of that as well but any software can have vulnerabilities.
I therefore do not recommend that you leave this software running for longer amounts of time.