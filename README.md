# Getting Started

### Why Github Analyzer

Github is a cool version controlling tool to avoid the phrase (OMG we need to integrate, send me your code via email) and (I think that i donno your contract :/ )
It's always good to keep an eye on the fast tracking numbers that facilitate the daily decisions. that's why
this project is considered.
The main goal is to integrate with Github via provided developer API [https://docs.github.com/en/rest] to load some required features not yet available by default for custome apps

So, building a web application that analyses GitHub repositories and
persists the results of the analysis is the goal

###### What is needed?
The user is requested to login through their GitHub account and is redirected to your
application after a successful login. The user is then shown a landing page with the following

* A form to submit a GitHub repository for analysis (i.e., owner and
repository name)
* A table showing the past analysis results of the logged in user in reverse
chronological order
* Each row in the table presents a report with the following information
* The URL of the repository
* Number of commits in the repository
* Number of open pull requests in the repository
* A link with text “Show Readme” that should open a widget/modal with
the README, if any in the repository

After a user submits a valid and public accessible GitHub repository for analysis, the
application should do the following

* Requests the results from the GitHub API
* Persists the results to show returning users their past analyses
* Refreshes the landing page with the new results

### Technologies Used
* Spring Umbrella [ Spring boot , Spring data , Spring security , REST ]
* gradle 
* Mustache (easy, cool, fast FE tech for backend developers :D )
* Docker ( I will not say oh it worked in my machine, everything is dockerized)
* Postgres
* Flyway ( dont worry , u dnt need to create db in your own )
* Kotlin
### Limitations (For now)
* More Tests Need to be added
* README -> as i'm not yet expert in Mustache , i just displayed the README in a [div] -> need to be popup
* Proper pagination need to be added to handle numOfPRs and numOfCommits accurately
* Pull Requests Count -> Currently as Github is Introducing pagination with max 100 result per page when specified and 30 result without specifying
                         And due to the [time factor and requests rate limits] i handled the num of PRs within 100
                         i.e. If the prs less than 100 you will get the accurate number , otherwise you will be 100 as maximum
                         The same for Commits
                         [Next Step to be Considered] -> Introduced proper pagination to retrieve accurate counts for more than 100 result

### How to Run
* [gradle clean build] (It will build the app for you in addition to generating needed flyway things)
* [docker-compose build && docker-compose up] (let's build and start our aoo WhooooHoo)
* http://localhost:8080 (You are here, Enjoy your time in my code)
* [docker exec -it <immoscout24_db_1> psql -U <- tc] in case you need to check your containerized DB, please check the container name via  [docker container ls -a]  
### The Expected Workflow
* By hitting [http://localhost:8080] you are in the main page where you are required to login using your github account
 (P.S.) You will not be able to come back if you are a logged in user
* after logging you will find yourself in the landing page where there are two sections (actually three with the banner)
   - Table that contains History searches
   - Form to submit your search input (repo owner , repo name)
   - In case of successful submition you will get repo analysis instantly shown in the table
   - In case of failing try , you will be redirected to error page , you would need to check logs for the error reason as it's handled in details their   
   
Finally : The code is under enhancements and adding extra features , just ping me for latest later
