#-------------------------------------------------
#
# Project created by QtCreator 2014-03-19T19:32:48
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = ChemicalThermalDynamicsCalculator
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    resultdialog.cpp \
    chemicaldatabase.cpp

HEADERS  += mainwindow.h \
    resultdialog.h \
    chemicaldatabase.h \
    chemicaldataentry.h

FORMS    += mainwindow.ui \
    resultdialog.ui
