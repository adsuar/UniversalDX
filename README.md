UniversalDX
===========

Task developed for the job position process.

REQUIREMENTS
============

OS: Linux
JVM: 1.6

Packages to Install
===================

In resources/R/packages.R we can find the different packages that we need to install:

1) RServe package (so we can invoke R actions)
2) e1071 package (so we can invoke the NaiveBayes classifier)

Rserve Launch
=============

1) R CMD Rserve
2) library(Rserve) 
   Rserve()

Execution
=========

The execution of the task has three ways:
a) Execution with Naive Bayes classifier: ant runNB
b) Execution with Support Vector Machines classifier: ant runSVM
c) Default execution (with the Naive Bayes classifier): ant run


NOTE: With the current test data, we can see a poor performance of SVM versus Naive Bayes (if type is C-classification of is nu-classification an the value of nu is for example 0.5) since the data used is stored as is in the training set and the predictions of SVM are wrong.

R Source Code
=============

Instead of creating two functions that trained the classifier in one hand, and predicted the result on the other hand, I've developed a third function focused on loading data, so I could reuse such functionality whenever were needed.

I have taken advantage of the fact that while the connection is opened, no data is lost (the session data is stored) so I can develop more light functions.
