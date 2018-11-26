from Enums import DATASET1
from Enums import DATASET2
from Enums import MODEL
from math import ceil
import numpy as np
from sklearn import tree
from sklearn import naive_bayes
from sklearn import svm
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import ParameterGrid
from sklearn.metrics import accuracy_score
from sklearn.metrics import classification_report
import pickle

class ModelTrainer_SVM_DS1_rbf :

    def __init__(self):
        self.instances = [];
        self.training_features = [];
        self.training_labels = [];
        self.validation_features = [];
        self.validation_labels = [];
        self.dataSet = '';
        self.model = '';

    def fetch_instances(self):
        with open(self.dataSet.HEADERS_FILE, 'r') as file:
            data = [line.split(',') for line in file.read().split('\n')][1:-1];
        self.instances = [y for x, y in data];

    def fetch_features(self):
        with open(self.dataSet.TRAINING_FILE, 'r') as file:
            data = [line.split(',') for line in file.read().split('\n')][0:-1];
        data = [[int(element) for element in row] for row in data]
        self.training_features = [d[:-1] for d in data]
        self.training_labels = [d[-1] for d in data]

    def fetch_validation_data(self):
        with open(self.dataSet.VALIDATION_FILE, 'r') as file:
            data = [line.split(',') for line in file.read().split('\n')][0:-1];
        data = [[int(element) for element in row] for row in data]
        self.validation_features = [d[:-1] for d in data]
        self.validation_labels = [d[-1] for d in data]

    def fetchData(self):
        self.fetch_instances();
        self.fetch_features();
        self.fetch_validation_data();

    def dt_max_depth(self):
        # experimenting Decision tree with max depth
        maximum_depths = np.linspace(1, 60, 60, endpoint=True)
        print('max_depth', ',', 'accuracy_training', ',', 'accuracy_validation');
        for max_depth in maximum_depths:
            dt = tree.DecisionTreeClassifier(max_depth=max_depth)
            dt.fit(self.training_features, self.training_labels)

            # predict training data
            training_predicted = dt.predict(self.training_features);
            accuracy_training = accuracy_score(self.training_labels, training_predicted)*100

            # predict validation data
            validation_predicted = dt.predict(self.validation_features)
            accuracy_validation = accuracy_score(self.validation_labels, validation_predicted)*100
            print(max_depth,',',accuracy_training,',',accuracy_validation);


    def dt_min_sample_split(self):
        # experimenting Decision tree with min sample split
        min_samples_splits = [i for i in range(2,200,4)]
        print('min_samples_split', ',', 'accuracy_training', ',', 'accuracy_validation');
        for min_samples_split in min_samples_splits:
            dt = tree.DecisionTreeClassifier(min_samples_split=min_samples_split)
            dt.fit(self.training_features, self.training_labels)

            # predict training data
            training_predicted = dt.predict(self.training_features);
            accuracy_training = accuracy_score(self.training_labels, training_predicted) * 100

            # predict validation data
            validation_predicted = dt.predict(self.validation_features)
            accuracy_validation = accuracy_score(self.validation_labels, validation_predicted) * 100
            print(min_samples_split, ',', accuracy_training, ',', accuracy_validation);

    def dt_min_sample_leaf(self):
        # experimenting Decision tree with min sample split
        print('min_samples_leaf', ',', 'accuracy_training', ',', 'accuracy_validation');
        min_samples_leafs = [int(i) for i in np.linspace(1, 100, 100, endpoint=True)]
        for min_samples_leaf in min_samples_leafs:
            dt = tree.DecisionTreeClassifier(min_samples_leaf=min_samples_leaf)
            dt.fit(self.training_features, self.training_labels)

            # predict training data
            training_predicted = dt.predict(self.training_features);
            accuracy_training = accuracy_score(self.training_labels, training_predicted) * 100

            # predict validation data
            validation_predicted = dt.predict(self.validation_features)
            accuracy_validation = accuracy_score(self.validation_labels, validation_predicted) * 100
            print(min_samples_leaf , ',', accuracy_training, ',',
                  accuracy_validation);

    def dt_max_features(self):
        # experimenting Decision tree with max features
        print('max features', ',', 'accuracy_training', ',', 'accuracy_validation');
        max_features = [int(i) for i in np.linspace(1, 100, 100, endpoint=True)]
        for max_feature in max_features:
            dt = tree.DecisionTreeClassifier(max_features=max_feature)
            dt.fit(self.training_features, self.training_labels)

            # predict training data
            training_predicted = dt.predict(self.training_features);
            accuracy_training = accuracy_score(self.training_labels, training_predicted) * 100

            # predict validation data
            validation_predicted = dt.predict(self.validation_features)
            accuracy_validation = accuracy_score(self.validation_labels, validation_predicted) * 100
            print(max_feature, ',', accuracy_training, ',',accuracy_validation);

    def experiment_dt(self):

        #experiment with max_depth
        self.dt_max_depth();

        #experiment with minimum sample split
        #self.dt_min_sample_split()


        #experiment with minimum samples at leaf
        #self.dt_min_sample_leaf()

        # experiment with max features
        #self.dt_max_features()


    def train_using_dt(self):
        classifier = tree.DecisionTreeClassifier(max_depth=24,min_samples_split=3,min_samples_leaf=1,max_features=99)
        classifier.fit(self.training_features, self.training_labels);

        validation_predicted = classifier.predict(self.validation_features);
        accuracy = accuracy_score(self.validation_labels, validation_predicted);

        print(accuracy*100);

        with open(MODEL.MODEL_DT, 'wb') as file:
            pickle.dump(classifier, file);

    def nb(self,classifier,alpha='-'):

        classifier.fit(self.training_features, self.training_labels)

        # predict training data
        training_predicted = classifier.predict(self.training_features);
        accuracy_training = accuracy_score(self.training_labels, training_predicted)*100

        # predict validation data
        validation_predicted = classifier.predict(self.validation_features)
        accuracy_validation = accuracy_score(self.validation_labels, validation_predicted)*100
        print(alpha,',',accuracy_training,',',accuracy_validation);


    def experiment_nb(self):
        # experiment with Gaussian NB
        print("Gaussian Naive Bayes");
        self.nb(naive_bayes.GaussianNB())

        #experiment with Multinomial NB, vary alpha
        alphas = np.linspace(0.1, 1.0, 10, endpoint=True)
        print("Multinomial Naive Bayes");
        for alpha in alphas:
            self.nb(naive_bayes.MultinomialNB(alpha=alpha),alpha)

        # experiment with Bernoulli NB, vary alpha
        print("Bernoulli Naive Bayes");
        for alpha in alphas:
            self.nb(naive_bayes.BernoulliNB(alpha=alpha),alpha)


    def train_using_nb(self):
        classifier = naive_bayes.BernoulliNB(alpha=0.5);
        classifier.fit(self.training_features, self.training_labels);

        validation_predicted = classifier.predict(self.validation_features);
        accuracy = accuracy_score(self.validation_labels, validation_predicted);

        print(accuracy * 100);

        with open(MODEL.MODEL_NB, 'wb') as file:
            pickle.dump(classifier, file);

    def experiment_svm(self):
        '''tuned_parameters = [{'kernel': ['rbf'],
                     'gamma': np.logspace(-4, 3, 8),
                     'C': [1e-3, 1e-2, 1e-1, 1, 10, 100, 1000]},
                    {'kernel': ['poly'],
                     'degree': [1, 2, 3, 4],
                     'C': [1e-3, 1e-2, 1e-1, 1, 10, 100, 1000],
                     'coef0': np.logspace(-4, 3, 8)},
                    {'kernel': ['linear'],
                     'C': [1e-3, 1e-2, 1e-1, 1, 10, 100, 1000]}]
        tuned_parameters = [{'kernel': ['rbf'],
                             'gamma': np.logspace(-1, 1, 3),
                             'C': [1e-1, 1 ]}]'''


        grid = [{'kernel': ['rbf'],
                 'gamma': np.logspace(-3, 2, 6),
                 'C': [1e-3, 1e-2, 1e-1, 1, 10, 100, 1000]}]

        paramGrid = ParameterGrid(grid)
        #print(len(paramGrid));
        f = open("results_svm_ds1_rbf.txt", "a+")
        i=0
        for params in paramGrid:
            i+=1
            classifier = svm.SVC(**params)
            classifier.fit(self.training_features, self.training_labels)

            # predict training data
            training_predicted = classifier.predict(self.training_features);
            accuracy_training = accuracy_score(self.training_labels, training_predicted) * 100

            # predict validation data
            validation_predicted = classifier.predict(self.validation_features)
            accuracy_validation = accuracy_score(self.validation_labels, validation_predicted) * 100
            f.write(str(params)+ ","+ str(accuracy_training)+","+str(accuracy_validation)+"\n")
            #print(params)
            if i%10==0:
                f.flush()

        f.close()

    def train_using_svm(self):
        classifier = svm.SVC()
        classifier.fit(self.training_features, self.training_labels);

        validation_predicted = classifier.predict(self.validation_features);
        accuracy = accuracy_score(self.validation_labels, validation_predicted);

        print(accuracy * 100);


if __name__ == '__main__':
    mt = ModelTrainer_SVM_DS1_rbf()
    mt.dataSet = DATASET1
    mt.fetchData()

    #print("Experimenting with Decision Trees Classifier")
    #mt.experiment_dt()

    #print("Training with Decision Trees Classifier using proper Hyperparameters got from experimenting above")
    #mt.train_using_dt()

    #print("Experimenting with Naive Bayes")
    #mt.experiment_nb()

    #print("Training with Naive Bayes Classifier using proper Hyperparameters got from experimenting above")
    #mt.train_using_nb()

    # print("Experimenting with SVM")
    mt.experiment_svm()

    # print("Training with SVM Classifier using proper Hyperparameters got from experimenting above")
    #mt.train_using_svm()

    '''
    print("Experimenting with Naive Bayes Classifier");
    mt.trainSystem(naive_bayes.MultinomialNB());

    print("Experimenting with SVM Classifier");
    mt.trainSystem(svm.SVC());


    dt= tree.DecisionTreeClassifier;
    '''

