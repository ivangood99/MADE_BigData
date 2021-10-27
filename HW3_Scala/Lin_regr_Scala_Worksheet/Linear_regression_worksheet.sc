import breeze.linalg._
import java.io._

val trainFileName = "/Users/gudkovivan/Documents/Programming/MADE/ML_in_BD/HW3_Scala/train.csv"
val testFileName = "/Users/gudkovivan/Documents/Programming/MADE/ML_in_BD/HW3_Scala/test.csv"


val trainFile = csvread(new File(trainFileName),',', skipLines = 1)
val testFile = csvread(new File(testFileName),',', skipLines = 1)



val train = DenseMatrix.horzcat(DenseVector.ones[Double](trainFile.rows).asDenseMatrix.reshape(trainFile.rows, 1), trainFile)
val test = DenseMatrix.horzcat(DenseVector.ones[Double](testFile.rows).asDenseMatrix.reshape(testFile.rows, 1), testFile)

val nFolds = 10

val rowsInOneFold = train.rows / nFolds

var bestWeights = DenseVector.zeros[Double](trainFile.cols)
val mistake = train(::, 0 until (train.cols - 1)) * bestWeights - train(::, train.cols - 1)
var minMSE = (mistake dot mistake) / train.rows
var bestFold = -1

for (i <- 0 until nFolds) {
  val xVal = train(i * rowsInOneFold until (i + 1) * rowsInOneFold, 0 until (train.cols - 1))
  val yVal = train(i * rowsInOneFold until (i + 1) * rowsInOneFold, train.cols - 1)

  val prefixXTrain = train(0 until i * rowsInOneFold, 0 until (train.cols - 1))
  val postfixXTrain = train((i + 1) * rowsInOneFold until train.rows, 0 until (train.cols - 1))
  val xTrain = DenseMatrix.vertcat(prefixXTrain,postfixXTrain)

  val prefixYTrain = train(0 until i * rowsInOneFold, train.cols - 1)
  val postfixYTrain = train((i + 1) * rowsInOneFold until train.rows, train.cols - 1)
  val yTrain = DenseVector.vertcat(prefixYTrain,postfixYTrain)

  val weights = pinv(xTrain) * yTrain
  val valMistake = xVal * weights - yVal
  val valMSE = (valMistake dot valMistake) / xVal.rows
  println(s"Fold ${i}. Weights: ${weights}")
  println(s"Fold ${i}. MSE: ${valMSE}")
  if (valMSE < minMSE) {
    minMSE = valMSE
    bestWeights = weights
    bestFold = i
  }
  println(s"Current best weights are from fold ${bestFold}: ${bestWeights}\n")
}

val yTest = test * bestWeights

//val answer = DenseMatrix.horzcat(test(::, 1 until test.cols), yTest.asDenseMatrix.reshape(test.rows,1))
val answer = yTest.asDenseMatrix.reshape(test.rows,1)

csvwrite(new File("/Users/gudkovivan/Documents/Programming/MADE/ML_in_BD/HW3_Scala/results.csv"), answer)

