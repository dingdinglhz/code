#ifndef CHEMICALDATAENTRY_H
#define CHEMICALDATAENTRY_H
#include <cstring>
class ChemicalDataEntry
{
    double enthalpy,entropy;
public:
    ChemicalDataEntry(){
    }

    ChemicalDataEntry(const double &enthalpy,const double &entropy)
    {
        this->enthalpy=enthalpy;
        this->entropy=entropy;
    }
    void setEnthalpy(const double &enthalpy){
        this->enthalpy=enthalpy;
    }
    void setEntropy(const double &entropy){
        this->entropy=entropy;
    }
    double getEnthalpy(){
        return enthalpy;
    }
    double getEntropy(){
        return entropy;
    }
    double getGibbsFreeEnergy(const double &temperature){
        return enthalpy*1000-entropy*temperature;
    }
};
#endif // CHEMICALDATAENTRY_H
