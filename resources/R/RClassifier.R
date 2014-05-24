# We load the Naive Bayes classifier
library(e1071)

# Function that loads the corpus
loadCorpus <- function(fileName) {
   corpus <- read.csv(file=fileName,head=FALSE,sep=",")
   return(corpus)
}

# Function that trains the Naive Bayes classifier
trainClassifierNB <- function(corpus) {
   # The class column should be categorical, not numeric.
   classifier <- naiveBayes(corpus[,2:16],as.factor(corpus[,1]))
   return(classifier)
}

# Function that trains the Support Vector Machines classifier
trainClassifierSVM <- function(corpus) {
   # The class column should be categorical, not numeric.
   classifier <- svm(corpus[,2:16],as.factor(corpus[,1]))
   return(classifier)
}

# Function that predicts to which class belongs a new entry
classify <- function(classifier,entriesToClassify) {
   prediction <- predict(classifier, entriesToClassify)
   return (prediction)
}