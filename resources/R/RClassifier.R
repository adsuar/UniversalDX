#leaves <- read.csv(file="/home/adsuar/workspace/trabajo/UniversalDX/resources/data/leaf.csv",head=FALSE,sep=",")

# We load the Naive Bayes classifier
library(e1071)

# Function that loads the corpus
loadCorpus <- function(fileName) {
   corpus <- read.csv(file=fileName,head=FALSE,sep=",")
   return(corpus)
}

# Function that trains the Naive Bayes classifier
trainClassifier <- function(corpus) {
   # HARDCODED - I'd be able to store in variables where the class
   #             is stored.
   #             Furthermore, the class column should be categorical,
   #             not numeric.
   classifier <- naiveBayes(corpus[,-1],as.factor(corpus[,1]))
   return(classifier)
}

# Function that predicts to which class belongs a new entry
classify <- function(classifier,entriesToClassify) {
   prediction <- predict(classifier, entriesToClassify)
   return (prediction)
}