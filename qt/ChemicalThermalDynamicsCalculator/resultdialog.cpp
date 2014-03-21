#include "resultdialog.h"
#include "ui_resultdialog.h"
#include "chemicaldataentry.h"
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

bool ResultDialog::getCoefficient(const QString &formula,String &formulaS,int &coefficient){

}

void ResultDialog::calculation(){
    QStringList sides=equation.remove(' ').remove('\n').split('=');
    if(sides.size()<2){
        QMessageBox::warning(this,"Invalid equation","Invalid equation. Please close the result dialog");
        destroy();
        return;
    }
    QStringList reactants=sides[0].split('+');
    QStringList products=sides[1].split('+');
    double enthalpyR=0,enthalpyP=0,entropyR=0,entropyP=0,gibbsR=0,gibbsP=0;

    foreach(QString formula,reactants){
        double enthalpy,entropy,gibbs;
        dataBase->getDataByFormula(formula.toStdString(),temperature,enthalpy,entropy,gibbs);
        enthalpyR+=enthalpy;
        entropyR+=entropy;
        gibbsR+=gibbs;
    }
    foreach(QString formula,products){
        double enthalpy,entropy,gibbs;
        dataBase->getDataByFormula(formula.toStdString(),temperature,enthalpy,entropy,gibbs);
        enthalpyP+=enthalpy;
        entropyP+=entropy;
        gibbsP+=gibbs;
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
