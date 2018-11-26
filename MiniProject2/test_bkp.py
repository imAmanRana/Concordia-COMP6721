from Enums import MODEL1
from Enums import MODEL2
from Enums import DATASET1
from Enums import DATASET2
import pickle;

class Test:
    test_data = []

    def load_test_data(self, path):
        with open(path,'r') as file:
            data =[line.split(',') for line in file.read().split('\n')][0:-1]
        self.test_data =[[int(element) for element in row] for row in data]

    def fetch_validation_data(self,path):
        with open(path, 'r') as file:
            data = [line.split(',') for line in file.read().split('\n')][0:-1];
        data = [[int(element) for element in row] for row in data]
        self.test_data = [d[:-1] for d in data]

    def run_naive_bayes(self,path,number):
        if number==1:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds1Val-nb.csv", predicts)
        else:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds2Val-nb.csv", predicts)



    def run_decision_tree(self,path,number):
        if number == 1:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds1Val-dt.csv", predicts)
        else:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds2Val-dt.csv", predicts)





    def run_svm(self, path,number):
        if number == 1:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds1Val-3.csv",predicts)
        else:
            with open(path, 'rb') as file:
                classifier = pickle.load(file)
            predicts = classifier.predict(self.test_data);
            self.write_to_file("./csv/ds2Val-3.csv", predicts)


    def write_to_file(self,filename,predicted):

        print("write to file",filename)
        with open(filename, 'w') as file:
            for i in range(len(predicted)):
                file.write('%d,%d\n' % (i + 1, predicted[i]))

def main():
    test = Test();
    inp = 0
    while inp != 7:
        print("1. Decision Tree DataSet1 ")
        print("2. Decision Tree DataSet2")
        print("3. Naive Bayes DataSet1 ")
        print("4. Naive Bayes DataSet2")
        print("5. SVM DataSet1")
        print("6. SVM DataSet2")
        print("7. Exit")
        inp = input("Please enter a value:")
        inp = int(inp)
        if inp == 1:
            test.fetch_validation_data(DATASET1.VALIDATION_FILE)
            test.run_decision_tree(MODEL1.MODEL_DT,1)
        elif inp == 2:
            test.fetch_validation_data(DATASET2.VALIDATION_FILE)
            test.run_decision_tree(MODEL2.MODEL_DT,2)
        elif inp == 3:
            test.fetch_validation_data(DATASET1.VALIDATION_FILE)
            test.run_naive_bayes(MODEL1.MODEL_NB,1)
        elif inp == 4:
            test.fetch_validation_data(DATASET2.VALIDATION_FILE)
            test.run_naive_bayes(MODEL2.MODEL_NB,2)
        elif inp == 5:
            test.fetch_validation_data(DATASET1.VALIDATION_FILE)
            test.run_svm(MODEL1.MODEL_SVM,1)
        elif inp == 6:
            test.fetch_validation_data(DATASET2.VALIDATION_FILE)
            test.run_svm(MODEL2.MODEL_SVM,2)
        elif inp == 7:
             break
        '''if inp == 1:
            test.load_test_data(DATASET1.TEST_FILE)
            test.run_decision_tree(MODEL1.MODEL_DT,1)
        elif inp == 2:
            test.load_test_data(DATASET2.TEST_FILE)
            test.run_decision_tree(MODEL2.MODEL_DT,2)
        elif inp == 3:
            test.load_test_data(DATASET1.TEST_FILE)
            test.run_naive_bayes(MODEL1.MODEL_NB,1)
        elif inp == 4:
            test.load_test_data(DATASET2.TEST_FILE)
            test.run_naive_bayes(MODEL2.MODEL_NB,2)
        elif inp == 5:
            test.load_test_data(DATASET1.TEST_FILE)
            test.run_svm(MODEL1.MODEL_SVM,1)
        elif inp == 6:
            test.load_test_data(DATASET2.TEST_FILE)
            test.run_svm(MODEL2.MODEL_SVM,2)
        elif inp == 7:
             break'''


if __name__ == '__main__':
    main()