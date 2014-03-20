#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QString>
#include <QTextEdit>
#include <QLabel>
#include <QDialogButtonBox>
#include <QInputDialog>
#include <QMessageBox>
#include <QAction>
#include <QDebug>

#include <fstream>
using std::ifstream;
#include "chemicaldatabase.h"
namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    
private:
    Ui::MainWindow *ui;

public slots:
    void setChemicalData();
    void setTemperature();
    void startCalculation();
    void displayHelp();
private:
    double temperature;
    static const double KELVIN_CONSTANT=273.15;
    void displayTemperature();
    ChemicalDataBase dataBase;
    void initialize();
};

#endif // MAINWINDOW_H
