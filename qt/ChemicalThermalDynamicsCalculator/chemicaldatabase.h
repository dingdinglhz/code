#ifndef CHEMICALDATABASE_H
#define CHEMICALDATABASE_H
#include <map>
#include <cstring>
#include <fstream>
#include "chemicaldataentry.h"
using namespace std;

class ChemicalDataBase
{
    map<string,ChemicalDataEntry> dataBase;
public:
    ChemicalDataBase();
    ChemicalDataEntry getChemicalByFormula(const string &formula);
    bool addChemical(const string &formula,const ChemicalDataEntry &data);
    bool deleteChemical(const string &formula);
    void readFromFile(ifstream in);
};


#endif // CHEMICALDATABASE_H
