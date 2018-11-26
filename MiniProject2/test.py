from Enums import MODEL1
from Enums import MODEL2
from Enums import DATASET1
from Enums import DATASET2
import pickle;

class Test:
    test_data = []
    dataset = ''
    is_val_data = False

    def load_test_data(self, path):
        with open(path,'r') as file:
            data =[line.split(',') for line in file.read().split('\n')][0:-1]
        self.test_data =[[int(element) for element in row] for row in data]
        if self.is_val_data:
            self.test_data = [d[:-1] for d in self.test_data]

    def run_naive_bayes(self,path,number,saveFile):

        with open(path, 'rb') as file:
            classifier = pickle.load(file)
        predicts = classifier.predict(self.test_data);
        self.write_to_file(saveFile, predicts)

    def run_decision_tree(self,path,number,saveFile):
        with open(path, 'rb') as file:
            classifier = pickle.load(file)
        predicts = classifier.predict(self.test_data);
        self.write_to_file(saveFile, predicts)

    def run_svm(self, path,number,saveFile):
        with open(path, 'rb') as file:
            classifier = pickle.load(file)
        predicts = classifier.predict(self.test_data);
        self.write_to_file(saveFile,predicts)

    def write_to_file(self,filename,predicted):
        with open(filename, 'w') as file:
            for i in range(len(predicted)):
                file.write('%d,%d\n' % (i + 1, predicted[i]))

def main():
    test = Test();
    inp = 0
    test.is_val_data=True  #change this to True for testing valildation data
    if(test.is_val_data):
        testFile1 = DATASET1.VALIDATION_FILE
        testFile2 = DATASET2.VALIDATION_FILE
        save_DT1 = DATASET1.DT_VAL
        save_DT2 = DATASET2.DT_VAL
        save_NB1 = DATASET1.NB_VAL
        save_NB2 = DATASET2.NB_VAL
        save_algo1 = DATASET1.ALGO3_VAL
        save_algo2 = DATASET2.ALGO3_VAL
    else:
        testFile1 = DATASET1.TEST_FILE
        testFile2 = DATASET2.TEST_FILE
        save_DT1 = DATASET1.DT_TEST
        save_DT2 = DATASET2.DT_TEST
        save_NB1 = DATASET1.NB_TEST
        save_NB2 = DATASET2.NB_TEST
        save_algo1 = DATASET1.ALGO3_TEST
        save_algo2 = DATASET2.ALGO3_TEST


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
            test.load_test_data(testFile1)
            test.run_decision_tree(DATASET1.MODEL_DT,1,save_DT1)
        elif inp == 2:
            test.load_test_data(testFile2)
            test.run_decision_tree(DATASET2.MODEL_DT,2,save_DT2)
        elif inp == 3:
            test.load_test_data(testFile1)
            test.run_naive_bayes(DATASET1.MODEL_NB,1,save_NB1)
        elif inp == 4:
            test.load_test_data(testFile2)
            test.run_naive_bayes(DATASET2.MODEL_NB,2,save_NB2)
        elif inp == 5:
            test.load_test_data(testFile1)
            test.run_svm(DATASET1.MODEL_SVM,1,save_algo1)
        elif inp == 6:
            test.load_test_data(testFile2)
            test.run_svm(DATASET2.MODEL_SVM,2,save_algo2)
        elif inp == 7:
             break


if __name__ == '__main__':
    main()