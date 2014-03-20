#include "chemicaldatabase.h"

ChemicalDataBase::ChemicalDataBase(){
    dataBase=map<string,ChemicalDataEntry>();

}
ChemicalDataEntry ChemicalDataBase::getChemicalByFormula(const string &formula){
    if(dataBase.count(formula)){
        return dataBase[formula];
    }else{
        return ChemicalDataEntry();
    }
}

bool ChemicalDataBase::addChemical(const string &formula,const ChemicalDataEntry &data){
    if(dataBase.count(formula)){
        return false;
    }else{
        dataBase[formula]=data;
        return true;
    }
}

bool ChemicalDataBase::deleteChemical(const string &formula){
    if(dataBase.count(formula)){
        dataBase.erase(dataBase.find(formula));
        return true;
    }else{
        return false;
    }
}

void ChemicalDataBase::readFromFile(ifstream in){
    string formula;
    double enthalpy,entropy;
    while(!in.eof()){
        in>>formula>>enthalpy>>entropy;
        addChemical(formula,ChemicalDataEntry(enthalpy,entropy));
    }
}
