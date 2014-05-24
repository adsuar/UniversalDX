UniversalDX
===========

Task developed for the job position process.

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
