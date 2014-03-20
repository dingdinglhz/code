#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QString>
#include <QTextEdit>
#include <QLabel>
#include <QDialogButtonBox>
#include <QInputDialog>
#include <QMessageBox>
#include <QAction>
#include <QDebug>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    connect(ui->actionSetChemicalData,&QAction::triggered,this,&MainWindow::setChemicalData);
    connect(ui->actionSetTemperature,&QAction::triggered,this,&MainWindow::setTemperature);
    connect(ui->buttonBox,&QDialogButtonBox::accepted,this,&MainWindow::startCalculation);
    connect(ui->buttonBox,&QDialogButtonBox::helpRequested,this,&MainWindow::displayHelp);
    connect(ui->buttonBox,&QDialogButtonBox::rejected,ui->equationTextEdit,&QTextEdit::clear);

    temperature=KELVIN_CONSTANT;
    displayTemperature();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::displayTemperature(){
    ui->environmentLabel->setText(tr("Temperature:")+QString::number(temperature)+"K");
}

void MainWindow::setChemicalData(){

}

void MainWindow::setTemperature(){
    bool ok;
    QString feedback=QInputDialog::getText(this,tr("Enter your temperature"),
                                           tr("Please enter your temperature.\nUse K/C/F to indicate unit."),
                                           QLineEdit::Normal,QString(),&ok);
    if(!ok){
        QMessageBox::warning(this,"Input cancelled","Temperature is not changed");
        return;
    }
    feedback=feedback.toUpper();
    QChar unit=feedback[feedback.size()-1];
    double tmp;
    bool valid;
    if(unit=='K'){
        tmp=feedback.remove('K').toDouble(&valid);
    }else if(unit=='C'){
        tmp=feedback.remove('C').toDouble(&valid)+KELVIN_CONSTANT;
    }else if(unit=='F'){
        tmp=(feedback.remove('F').toDouble(&valid)-32)/1.8+KELVIN_CONSTANT;
    }else{
        tmp=feedback.toDouble(&valid);
    }
    if(!valid){
        QMessageBox::warning(this,"Invalid input","The input is not a valid temperature!");
    }
    else{
        temperature=tmp;
    }
    displayTemperature();
}

void MainWindow::startCalculation(){

}

void MainWindow::displayHelp(){

}

