#include "resultdialog.h"
#include "ui_resultdialog.h"
#include "chemicaldataentry.h"
ResultDialog::ResultDialog(QWidget *parent,QString equation) :
    QDialog(parent),
    ui(new Ui::ResultDialog)
{
    ui->setupUi(this);

    connect(ui->buttonBox,&QDialogButtonBox::accepted,this,&QDialog::close);

    this->equation=equation;
    calculation();
}

ResultDialog::~ResultDialog()
{
    delete ui;
}

void ResultDialog::calculation(){
    QStringList sides=equation.remove(' ').split('=');
    if(sides.size()<2){
        QMessageBox::warning(this,"Invalid equation","Invalid equation. Please close the result dialog");
        //destroy();
        return;
    }
    QStringList reactants=sides[0].split('+');
    QStringList products=sides[1].split('+');
    double enthalpyR,enthalpyP,entropyR,entropyP;
    foreach(QString formula,reactants){
        ChemicalDataEntry chemical=
    }
}
