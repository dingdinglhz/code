#ifndef CHEMICALDATABASE_H
#define CHEMICALDATABASE_H
#include <map>
#include <cstring>
#include <fstream>
#include <iostream>
#include "chemicaldataentry.h"
using std::string;
using std::ifstream;
using std::map;

class ChemicalDataBase
{
    map<string,ChemicalDataEntry> dataBase;
    double tempAtMeasure;
public:
    ChemicalDataBase(double tempAtMeasure=0);
    ChemicalDataEntry getChemicalByFormula(const string &formula);
    bool addChemical(const string &formula,const ChemicalDataEntry &data);
    bool deleteChemical(const string &formula);
    void readFromFile(ifstream &in);
    bool getDataByFormula(const string &formula,const double &temperature,double &enthalpy,double &entropy,double &gibbs);
};


#endif // CHEMICALDATABASE_H
