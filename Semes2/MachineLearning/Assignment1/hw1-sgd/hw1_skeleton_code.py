import pandas as pd
import logging
import numpy as np
import sys
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from sklearn.cross_validation import train_test_split
import pdb 

### Assignment Owner: Keeyon Ebrahimi

#######################################
####Q2.1: Normalization

def feature_normalization(train, test):
    """Rescale the data so that each feature in the training set is in
    the interval [0,1], and apply the same transformations to the test
    set, using the statistics computed on the training set.
    
    Args:
        train - training set, a 2D numpy array of size (num_instances, num_features)
        test  - test set, a 2D numpy array of size (num_instances, num_features)
    Returns:
        train_normalized - training set after normalization
        test_normalized  - test set after normalization

    """
    ## TODONE
    trainMin = np.resize(train.min(axis=0), (100, 48))
    trainMax = np.resize(train.max(axis=0), (100, 48))

    topTrain = train - trainMin
    bottomTrain = trainMax - trainMin
    outputTrain = topTrain/bottomTrain
    
    topTest = test - trainMin
    bottomTest = bottomTrain
    outputTest = topTest/bottomTest
    
    return outputTrain, outputTest
########################################
####Q2.2a: The square loss function

def compute_square_loss(X, y, theta):
    """
    Given a set of X, y, theta, compute the square loss for predicting y with X*theta
    
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)

        y - the label vector, 1D numpy array of size (num_instances)
        theta - the parameter vector, 1D array of size (num_features)
    
    Returns:
        loss - the square loss, scalar
    """
    #pdb.set_trace()
    #(X Theta - y)
    originalOperation = np.dot(X, theta) - y
    #Orig Transpose * orig
    loss = np.dot(originalOperation.T, originalOperation)
    #loss = 0 #initialize the square_loss
    return loss
    #TODO
    


########################################
###Q2.2b: compute the gradient of square loss function
def compute_square_loss_gradient(X, y, theta):
    """
    Compute gradient of the square loss (as defined in compute_square_loss), at the point theta.
    
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        theta - the parameter vector, 1D numpy array of size (num_features)
    
    Returns:
        grad - gradient vector, 1D numpy array of size (num_features)
    """
    #TODO
    #X.t * x * theta - X.t * y
    Xsquared = np.dot(X.T, X)
    leftHand = np.dot(Xsquared, theta)
    rightHand = np.dot(X.T, y)
    return leftHand - rightHand

    
       
        
###########################################
###Q2.3a: Gradient Checker
#Getting the gradient calculation correct is often the trickiest part
#of any gradient-based optimization algorithm.  Fortunately, it's very
#easy to check that the gradient calculation is correct using the
#definition of gradient.
#See http://ufldl.stanford.edu/wiki/index.php/Gradient_checking_and_advanced_optimization
def grad_checker(X, y, theta, epsilon=0.01, tolerance=1e-4): 
    """Implement Gradient Checker
    Check that the function compute_square_loss_gradient returns the
    correct gradient for the given X, y, and theta.

    Let d be the number of features. Here we numerically estimate the
    gradient by approximating the directional derivative in each of
    the d coordinate directions: 
    (e_1 = (1,0,0,...,0), e_2 = (0,1,0,...,0), ..., e_d = (0,...,0,1) 

    The approximation for the directional derivative of J at the point
    theta in the direction e_i is given by: 
    ( J(theta + epsilon * e_i) - J(theta - epsilon * e_i) ) / (2*epsilon).

    We then look at the Euclidean distance between the gradient
    computed using this approximation and the gradient computed by
    compute_square_loss_gradient(X, y, theta).  If the Euclidean
    distance exceeds tolerance, we say the gradient is incorrect.

    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        theta - the parameter vector, 1D numpy array of size (num_features)
        epsilon - the epsilon used in approximation
        tolerance - the tolerance error
    
    Return:
        A boolean value indicate whether the gradient is correct or not

    """
    true_gradient = compute_square_loss_gradient(X, y, theta) #the true gradient
    num_features = theta.shape[0]
    approx_grad = np.zeros(num_features) #Initialize the gradient we approximate

    # Check addition of theta and epsilon
    epsilonArray = np.full(theta.shape, epsilon)
    print "WE CAN TEST NOW"
    EpsilonPlusLoss = compare_square_loss(X, y, theta + epsilonArray)
    EpsilonMinusLoss = compare_square_loss(X, y, theta - epsilonArray)
    EstimatedGrad = (EpsilonPlussLoss - EpsilonMinusLoss) / (2 * epsilon)
    EuclidDistance = numpy.linalg.norm(true_gradient - EstimatedGrad)
    if (EuclidDistance > tolerance):
        return false
    else:
        return true
    #TODO
    
#################################################
###Q2.3b: Generic Gradient Checker
def generic_gradient_checker(X, y, theta, objective_func, gradient_func, epsilon=0.01, tolerance=1e-4):
    """
    The functions takes objective_func and gradient_func as parameters. And check whether gradient_func(X, y, theta) returned
    the true gradient for objective_func(X, y, theta).
    Eg: In LSR, the objective_func = compute_square_loss, and gradient_func = compute_square_loss_gradient
    """
    #TODO


####################################
####Q2.4a: Batch Gradient Descent
def batch_grad_descent(X, y, alpha=0.1, num_iter=1000, check_gradient=False):
    """
    In this question you will implement batch gradient descent to
    minimize the square loss objective
    
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        alpha - step size in gradient descent
        num_iter - number of iterations to run 
        check_gradient - a boolean value indicating whether checking the gradient when updating
        
    Returns:
        theta_hist - store the the history of parameter vector in iteration, 2D numpy array of size (num_iter+1, num_features) 
                    for instance, theta in iteration 0 should be theta_hist[0], theta in ieration (num_iter) is theta_hist[-1]
        loss_hist - the history of objective function vector, 1D numpy array of size (num_iter+1) 
    """
    num_instances, num_features = X.shape[0], X.shape[1]
    theta_hist = np.zeros((num_iter+1, num_features))  #Initialize theta_hist
    loss_hist = np.zeros(num_iter+1) #initialize loss_hist
    theta = np.ones(num_features) #initialize theta
    for i in range(0, num_iter+1):
        loss_hist[i] = compute_square_loss(X, y, theta)
        theta_hist[i] = theta
        squareLossGrad = compute_square_loss_gradient(X, y, theta)
        theta = theta - alpha*(squareLossGrad/np.linalg.norm(squareLossGrad))


    return theta_hist, loss_hist
    #TODO

####################################
###Q2.4b: Implement backtracking line search in batch_gradient_descent
###Check http://en.wikipedia.org/wiki/Backtracking_line_search for details
#TODO
    
def compute_regularized_square_loss(X, y, theta, lambda_reg):
    SquareLossLeft = compute_square_loss(X, y, theta)
    SquareLossRight = (lambda_reg/2) * np.dot(theta.T, theta)
    return SquareLossLeft + SquareLossRight

###################################################
###Q2.5a: Compute the gradient of Regularized Batch Gradient Descent
def compute_regularized_square_loss_gradient(X, y, theta, lambda_reg):
    """
    Compute the gradient of L2-regularized square loss function given X, y and theta
    
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        theta - the parameter vector, 1D numpy array of size (num_features)
        lambda_reg - the regularization coefficient
    
    Returns:
        grad - gradient vector, 1D numpy array of size (num_features)
    """
    #TODO
    (num_instances, num_features) = X.shape
    LambdaI = lambda_reg * np.identity(num_features)
    #X.t X + LambdaI
    XSquaredPlusLambdaI = np.dot(X.T, X) + LambdaI
    LeftEquation = np.linalg.inv(XSquaredPlusLambdaI)
    RightEquation = np.dot(X.T, y)
    return np.dot(LeftEquation, RightEquation)

###################################################
###Q2.5b: Batch Gradient Descent with regularization term
def regularized_grad_descent(X, y, alpha=0.1, lambda_reg=1, num_iter=1000):
    """
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        alpha - step size in gradient descent
        lambda_reg - the regularization coefficient
        numIter - number of iterations to run 
        
    Returns:
        theta_hist - the history of parameter vector, 2D numpy array of size (num_iter+1, num_features) 
        loss_hist - the history of regularized loss value, 1D numpy array
    """
    (num_instances, num_features) = X.shape
    theta = np.ones(num_features) #Initialize theta
    theta_hist = np.zeros((num_iter+1, num_features))  #Initialize theta_hist
    loss_hist = np.zeros(num_iter+1) #Initialize loss_hist
    #TODO
    
    for i in range(0, num_iter+1):
        loss_hist[i] = compute_regularized_square_loss(X, y, theta, lambda_reg)
        theta_hist[i] = theta
        LossGrad = compute_regularized_square_loss_gradient(X, y, theta, lambda_reg)
        theta = theta - alpha*(LossGrad/np.linalg.norm(LossGrad))

    return theta_hist, loss_hist
#############################################
##Q2.5c: Visualization of Regularized Batch Gradient Descent
##X-axis: log(lambda_reg)
##Y-axis: square_loss

def visualized_reg_batch_desc(X, y):
    # lambda_array = np.array([0.001, 0.01, 1, 10, 100])
    # converged_thetas = np.empty([X.shape[1], 5])

    # for i in range(0, lambda_array.size):
    #     theta_hist, loss_hist = regularized_grad_descent(X, y, 0.1, lambda_array[i])
    #     current_theta_converged = theta_hist[-1,:]
    #     converged_thetas[:, i] = current_theta_converged
    

    # converged_thetas = np.empty([X.shape[1], 5])

    converged_thetas = np.empty([X.shape[1], X.shape[1]])
    x = np.linspace(0, 48, converged_thetas.shape[0])
    y = np.linspace(0, 100, converged_thetas.shape[0]) 
    print y.size
    print y.flat[3]
    for i in range(0, y.size):
        theta_hist, loss_hist = regularized_grad_descent(X, y, 0.1, y.flat[i])
        current_theta_converged = theta_hist[-1,:]
        converged_thetas[:, i] = current_theta_converged



    #y = np.linspace(0, 4, converged_thetas.shape[1])
    data = converged_thetas
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    #ax.plot(x, y, data, label='Data')
    #ax.legend()

    #a = hf.add_subplot(111, projection='3d')
    #a = Axes3D(hf)
    #X, Y = np.meshgrid(x, y)
    print x.shape
    print y.shape
    print data.shape
    ax.plot_surface(x, y, data, color='b')
    #plt.plot(lambda_array, converged_thetas.T)
    #plt.plot(converged_thetas.T)
    #plt.plot(converged_thetas)
    plt.title("Thetas with different regularizations")
    plt.show()
    plt.savefig('RegBatchGradDesc.png')
#############################################
###Q2.6a: Stochastic Gradient Descent
def stochastic_grad_descent(X, y, alpha=0.1, lambda_reg=1, num_iter=1000):
    """
    In this question you will implement stochastic gradient descent with a regularization term
    
    Args:
        X - the feature vector, 2D numpy array of size (num_instances, num_features)
        y - the label vector, 1D numpy array of size (num_instances)
        alpha - string or float. step size in gradient descent
                NOTE: In SGD, it's not always a good idea to use a fixed step size. Usually it's set to 1/sqrt(t) or 1/t
                if alpha is a float, then the step size in every iteration is alpha.
                if alpha == "1/sqrt(t)", alpha = 1/sqrt(t)
                if alpha == "1/t", alpha = 1/t
        lambda_reg - the regularization coefficient
        num_iter - number of epochs (i.e number of times) to go through the whole training set
    
    Returns:
        theta_hist - the history of parameter vector, 3D numpy array of size (num_iter, num_instances, num_features) 
        loss hist - the history of regularized loss function vector, 2D numpy array of size(num_iter, num_instances)
    """
    num_instances, num_features = X.shape[0], X.shape[1]
    theta = np.ones(num_features) #Initialize theta
    
    
    theta_hist = np.zeros((num_iter, num_instances, num_features))  #Initialize theta_hist
    loss_hist = np.zeros((num_iter, num_instances)) #Initialize loss_hist
    #TODO

################################################
###Q2.6b Visualization that compares the convergence speed of batch
###and stochastic gradient descent for various approaches to step_size
##X-axis: Step number (for gradient descent) or Epoch (for SGD)
##Y-axis: log(objective_function_value)

def main():
    #Loading the dataset

    df = pd.read_csv('hw1-data.csv', delimiter=',')
    X = df.values[:,:-1]
    y = df.values[:,-1]

    print('Split into Train and Test')


    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size =100, random_state=10)

    print("Scaling all to [0, 1]")
    X_train, X_test = feature_normalization(X_train, X_test)
    X_train = np.hstack((X_train, np.ones((X_train.shape[0], 1)))) #Add bias term
    X_test = np.hstack((X_test, np.ones((X_test.shape[0], 1)))) #Add bias term

    visualized_reg_batch_desc(X_train, y_train)
   # theta_hist, lost_hist = batch_grad_descent(X_train, y_train, 3)
   # plt.plot(lost_hist)
   # plt.title("Step Size 3")
   # plt.savefig('GradDescStepSize3.png')
    ## plt.show()
    #TODO

    
if __name__ == "__main__":
    main()
    
