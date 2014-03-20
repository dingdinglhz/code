#ifndef RESULTDIALOG_H
#define RESULTDIALOG_H

#include <QDialog>
#include <QString>
//#include <QTextEdit>
#include <QLabel>
#include <QDialogButtonBox>
#include <QMessageBox>
#include <QDebug>
namespace Ui {
class ResultDialog;
}

class ResultDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit ResultDialog(QWidget *parent = 0, QString equation=QString());
    ~ResultDialog();
    
private:
    Ui::ResultDialog *ui;

private:
    QString equation;
    void calculation();
};

#endif // RESULTDIALOG_H
