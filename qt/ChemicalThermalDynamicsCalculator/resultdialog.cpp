#include "resultdialog.h"
#include "ui_resultdialog.h"
ResultDialog::ResultDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ResultDialog)
{
    ui->setupUi(this);

    connect(ui->buttonBox,&QDialogButtonBox::accepted,this,&QDialog::close);

    this->equation=equation;
}

ResultDialog::~ResultDialog()
{
    delete ui;
}
void ResultDialog::setup(QString equation, ChemicalDataBase &dataBase, double temperature){
    this->equation=equation;
    this->dataBase=&dataBase;
    this->temperature=temperature;
    calculation();
}

bool ResultDialog::getCoefficient(const QString &formula,string &formulaS,int &coefficient){
    for(int i=0;i<formula.size();i++){
        if(formula.at(i).isLetter()){
            coefficient=formula.left(i).toInt();
            if(i==0){coefficient=1;}
            formulaS=formula.right(formula.size()-i).toStdString();
            return true;
        }
    }
    return false;
}

void ResultDialog::calculation(){
    QStringList sides=equation.remove(' ').remove('\n').split('=');
    if(sides.size()<2){
        QMessageBox::warning(this,"Invalid equation",
                             "Invalid equation. Please close the result dialog");
        destroy();
        return;
    }
    QStringList reactants=sides[0].split('+');
    QStringList products=sides[1].split('+');
    double enthalpyR=0,enthalpyP=0,entropyR=0,entropyP=0,gibbsR=0,gibbsP=0;

    foreach(QString formula,reactants){
        int coef;
        std::string formulaS;
        if(getCoefficient(formula,formulaS,coef)){
            double enthalpy,entropy,gibbs;
            if(dataBase->getDataByFormula(formulaS,temperature,enthalpy,entropy,gibbs)){
                enthalpyR+=coef*enthalpy;
                entropyR+=coef*entropy;
                gibbsR+=coef*gibbs;
            }
            else{
                QMessageBox::warning(this,"Chemical not found",
                                     QString().append("Unable to find chemical:\n").append(formulaS.c_str()));
            }
        }else{
            QMessageBox::warning(this,"Invalid formula","Invalid chemical formula:\n"+formula);
        }
    }
    foreach(QString formula,products){
        int coef;
        std::string formulaS;
        if(getCoefficient(formula,formulaS,coef)){
            double enthalpy,entropy,gibbs;
            if(dataBase->getDataByFormula(formulaS,temperature,enthalpy,entropy,gibbs)){
                enthalpyP+=coef*enthalpy;
                entropyP+=coef*entropy;
                gibbsP+=coef*gibbs;
            }
            else{
                QMessageBox::warning(this,"Chemical not found",
                                     QString().append("Unable to find chemical:\n").append(formulaS.c_str()));
            }
        }else{
            QMessageBox::warning(this,"Invalid formula","Invalid chemical formula:\n"+formula);
        }
    }
    QString result=QString()+"Final Result:\nTotal enthalpy change:"
            +QString::number(enthalpyP-enthalpyR)+"kJ/mol \nTotal entropy change:"
            +QString::number(entropyP-entropyR)+"J/mol*K \nTotal gibbs free energy change:"
            +QString::number(gibbsP-gibbsR)+"kJ/mol\n";
    if(enthalpyP>enthalpyR){result+=" -endothermic reaction- ";}
    else{result+=" -exothermic reaction- /n";}
    if(entropyP>entropyR){result+=" -entropy increase- /n";}
    else{result+=" -entropy decrease- /n";}
    if(gibbsP>gibbsR){result+=" -non spontaneous- /n";}
    else{result+=" -spontaneous- /n";}
    ui->resultLabel->setText(result);

}
//test equation: Ca+Cl2=CaCl2
