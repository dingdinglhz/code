#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "resultdialog.h"

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

    initialize();

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::initialize(){
    temperature=KELVIN_CONSTANT;
    displayTemperature();
    ifstream inf("chemicalData.txt", ifstream::in);
    //inf.open();
    if(inf.is_open()){
        dataBase.readFromFile(inf);
        qDebug()<<"open data file";

    }else{
        QMessageBox::warning(this,"No valid chemical data base!",
                             "Please enter chemical thermodynamics data in \"chemicalData.txt\" !");
    }
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
    ResultDialog *dialog=new ResultDialog(this);
    dialog->show();
    dialog->setup(ui->equationTextEdit->toPlainText(),dataBase,temperature);

}

void MainWindow::displayHelp(){

}

