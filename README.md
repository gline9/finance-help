
![build status](https://travis-ci.org/gline9/finance-help.svg?branch=master)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gline9_finance-help&metric=alert_status)](https://sonarcloud.io/dashboard?id=gline9_finance-help)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=gline9_finance-help&metric=coverage)](https://sonarcloud.io/dashboard?id=gline9_finance-help)[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=gline9_finance-help&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=gline9_finance-help)[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=gline9_finance-help&metric=bugs)](https://sonarcloud.io/dashboard?id=gline9_finance-help)[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=gline9_finance-help&metric=code_smells)](https://sonarcloud.io/dashboard?id=gline9_finance-help)

# finance-help

Basic program for helping with managing personal finances.

# Contributing

We use grunt to coordinate builds across angular-cli and gradle. To
build the project you will need to have the grunt-cli installed globally.
To do this run:

```bash
npm i -g grunt-cli
```

Once grunt is installed you will need to run the following commands
inside `electron/finance-help` to get the program running.

```bash
npm i
npm run electron-full
```

## Front End Only

Once the backend has been built using `npm run electron-full` you only
need to run `npm run electron` to re-build the front end.

## Back End Only

If you only want to test out rest calls on the backend it is enough
to run `npm run backend`


