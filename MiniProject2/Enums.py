from sklearn import tree;
from sklearn import naive_bayes;
from sklearn import svm;

def enum(**enums):
    return type('Enum', (), enums)


DATASET1 = enum(
    HEADERS_FILE="./dataset/ds1/ds1Info.csv",
    TRAINING_FILE = "./dataset/ds1/ds1Train.csv",
    VALIDATION_FILE = "./dataset/ds1/ds1Val.csv",
    TEST_FILE = "./dataset/ds1/ds1Test.csv",
    DT_VAL = "./csv/ds1Val-dt.csv",
    DT_TEST= "./csv/ds1Test-dt.csv",
    NB_VAL = "./csv/ds1Val-nb.csv",
    NB_TEST = "./csv/ds1Test-nb.csv",
    ALGO3_VAL = "./csv/ds1Val-3.csv",
    ALGO3_TEST = "./csv/ds1Test-3.csv",
    MODEL_DT="./model/model1/DT.pkl",
    MODEL_NB = "./model/model1/NB.pkl",
    MODEL_SVM = "./model/model1/SVM.pkl"
)

DATASET2 = enum(
    HEADERS_FILE="./dataset/ds2/ds2Info.csv",
    TRAINING_FILE = "./dataset/ds2/ds2Train.csv",
    VALIDATION_FILE = "./dataset/ds2/ds2Val.csv",
    TEST_FILE="./dataset/ds2/ds2Test.csv",
    DT_VAL = "./csv/ds2Val-dt.csv",
    DT_TEST= "./csv/ds2Test-dt.csv",
    NB_VAL = "./csv/ds2Val-nb.csv",
    NB_TEST = "./csv/ds2Test-nb.csv",
    ALGO3_VAL = "./csv/ds2Val-3.csv",
    ALGO3_TEST = "./csv/ds2Test-3.csv",
    MODEL_DT="./model/model2/DT.pkl",
    MODEL_NB = "./model/model2/NB.pkl",
    MODEL_SVM = "./model/model2/SVM.pkl"
)

MODEL1 = enum(
    MODEL_DT="./model/model1/DT.pkl",
    MODEL_NB = "./model/model1/NB.pkl",
    MODEL_SVM = "./model/model1/SVM.pkl"
)

MODEL2 = enum(
    MODEL_DT="./model/model2/DT.pkl",
    MODEL_NB = "./model/model2/NB.pkl",
    MODEL_SVM = "./model/model2/SVM.pkl"
)


