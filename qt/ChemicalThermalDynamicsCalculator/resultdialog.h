#ifndef RESULTDIALOG_H
#define RESULTDIALOG_H

#include <QDialog>

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
};

#endif // RESULTDIALOG_H
