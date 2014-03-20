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
        ChemicalDataEntry chemical=dataBase->getChemicalByFormula(formula.toStdString());
        qDebug()<<formula<<" "<<chemical.getEnthalpy()<<" "<<chemical.getEntropy()
           <<" "<<chemical.getGibbsFreeEnergy(temperature)<<endl;
        enthalpyR+=chemical.getEnthalpy();
        entropyR+=chemical.getEntropy();
        gibbsR+=chemical.getGibbsFreeEnergy(temperature);
    }
    foreach(QString formula,products){
        ChemicalDataEntry chemical=dataBase->getChemicalByFormula(formula.toStdString());
        qDebug()<<formula<<" "<<chemical.getEnthalpy()<<" "<<chemical.getEntropy()
           <<" "<<chemical.getGibbsFreeEnergy(temperature)<<endl;
        enthalpyP+=chemical.getEnthalpy();
        entropyP+=chemical.getEntropy();
        gibbsP+=chemical.getGibbsFreeEnergy(temperature);
    }
    QString result=QString()+"Final Result:\nTotal enthalpy change:"
            +QString::number(enthalpyP-enthalpyR)+"kJ/mol \nTotal entropy change:"
            +QString::number(entropyP-entropyR)+"kJ/mol*K \nTotal gibbs free energy change:"
            +QString::number((gibbsP-gibbsR)/1000)+"kJ/mol\n";
    if(enthalpyP>enthalpyR){result+=" -endothermic reaction- ";}
    else{result+=" -exothermic reaction- ";}
    if(entropyP>entropyR){result+=" -entropy increase- ";}
    else{result+=" -entropy decrease- ";}
    if(gibbsP>gibbsR){result+=" -non spontaneous- ";}
    else{result+=" -spontaneous- ";}
    ui->resultLabel->setText(result);

}
