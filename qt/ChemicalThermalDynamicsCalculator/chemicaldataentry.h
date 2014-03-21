#ifndef CHEMICALDATAENTRY_H
#define CHEMICALDATAENTRY_H
#include <cstring>
class ChemicalDataEntry
{
    double enthalpy,entropy,gibbs;//units: kJ/mol J/mol*K kJ/mol
public:
    ChemicalDataEntry(){
    }

    ChemicalDataEntry(const double &enthalpy,const double &entropy,const double &gibbs)
    {
        this->enthalpy=enthalpy;
        this->entropy=entropy;
        this->gibbs=gibbs;
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
    double getGibbsFreeEnergy(const double &temperature,const double &tempAtMeasure=0){
        return gibbs-entropy*(temperature-tempAtMeasure)/1000;
    }
};
#endif // CHEMICALDATAENTRY_H
