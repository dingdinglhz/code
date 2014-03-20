#ifndef RESULTDIALOG_H
#define RESULTDIALOG_H

#include <QDialog>
#include <QString>
//#include <QTextEdit>
#include <QLabel>
#include <QDialogButtonBox>
#include <QMessageBox>
#include <QDebug>
#include "chemicaldatabase.h"
namespace Ui {
class ResultDialog;
}

class ResultDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit ResultDialog(QWidget *parent = 0);
    ~ResultDialog();
    
private:
    Ui::ResultDialog *ui;
public:
    void setup(QString equation, ChemicalDataBase &dataBase, double temperature);
private:
    QString equation;
    ChemicalDataBase *dataBase;
    double temperature;
    void calculation();
};

#endif // RESULTDIALOG_H
