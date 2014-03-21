#include "chemicaldatabase.h"

ChemicalDataBase::ChemicalDataBase(double tempAtMeasure){
    dataBase=map<string,ChemicalDataEntry>();
    this->tempAtMeasure=tempAtMeasure;
}
ChemicalDataEntry ChemicalDataBase::getChemicalByFormula(const string &formula){
    if(dataBase.count(formula)){
        return dataBase[formula];
    }else{
        return ChemicalDataEntry();
    }
}
bool ChemicalDataBase::getDataByFormula(const string &formula,const double &temperature,
                                        double &enthalpy,double &entropy,double &gibbs){
    if(dataBase.count(formula)){
        ChemicalDataEntry chemical=dataBase[formula];
        enthalpy=chemical.getEnthalpy();
        entropy=chemical.getEntropy();
        gibbs=chemical.getGibbsFreeEnergy(temperature,tempAtMeasure);
        return true;
    }else{
        return false;
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

void ChemicalDataBase::readFromFile(ifstream &in){
    string formula;
    double enthalpy,entropy,gibbs;
    in>>tempAtMeasure;
    while(!in.eof()){
        in>>formula>>enthalpy>>entropy>>gibbs;
        addChemical(formula,ChemicalDataEntry(enthalpy,entropy,gibbs));
    }
    /*for (std::map<string,ChemicalDataEntry>::iterator it=dataBase.begin(); it!=dataBase.end(); ++it){
        cout<<(it->first)<<" "<<(it->second.getEnthalpy())<<" "<<(it->second.getEntropy())
           <<" "<<(it->second.getGibbsFreeEnergy(100))<<endl;
    }*/
}
