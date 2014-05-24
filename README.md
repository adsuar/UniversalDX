UniversalDX
===========

Task developed for the job position process.

Requirements
------------

- OS: Linux
- JVM: 1.6

Training Data
-------------

I've used the Leaf Data Set to train the classifier. Such data set is stored at resources/data/leaf.csv.

Packages to Install
-------------------

In **resources/R/packages.R** we can find the different packages that we need to install:

- *RServe* package (so we can invoke R actions)
- *e1071* package (so we can invoke the NaiveBayes classifier)

Rserve Launch
-------------

The different ways for launching the RServe process that I've tested to make this task have been the following ones:

- R CMD Rserve
- library(Rserve); Rserve()

Execution
---------

The execution of the task has three ways:
- Execution with **Naive Bayes** classifier: **ant runNB**
- Execution with Support Vector Machines classifier: **ant runSVM**
- **Default** execution (with the Naive Bayes classifier): **ant run**

At resources/config/universaldx.properties you can define which will be the training data set and the test data set. Furthermore, you can set the column in which the class will be stored at the training data set, so you can change the training set easily.

**NOTE**: *With the current test data, we can see a poor performance of SVM versus Naive Bayes (if type is C-classification of is nu-classification and the value of nu is for example 0.5) since the data used is stored as is in the training set and the predictions of SVM are wrong.*

R Source Code
-------------

Instead of creating just two functions that trained the classifier in one hand, and predicted the result on the other hand, I've developed a third function focused on loading data, so I could reuse such functionality whenever were needed.

I have taken advantage of the fact that while the connection is opened, no data is lost (the session data is stored) so I can develop lighter functions.

The code is at: resources/R/RClassifier.R
