/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.Accountancy.CreditorsNdebtorsController;
import java.io.InputStream;
import java.util.HashMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;

/**
 *
 * @author WYNXTYN PROAUTOTYPE
 */
public class report_services {

    public report_services() {
    }

    public Service<Boolean> load_ps_report(String sql, Connection conn, HashMap reportHash) {
        Service<Boolean> service;
        service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {

                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            updateMessage("Please wait while generate report");
                            InputStream is = report_services.class.getResourceAsStream("/com/reports/r_debtorsCreditors.jrxml");
                            JasperDesign jd = JRXmlLoader.load(is);

                            JRDesignQuery newQuery = new JRDesignQuery();
                            newQuery.setText(sql);//"Select * from livewell.account_patientsection" sql

                            jd.setQuery(newQuery);

                            JasperReport jr = JasperCompileManager.compileReport(jd);
                            JasperPrint jp = JasperFillManager.fillReport(jr, reportHash, conn);
                            JasperViewer.viewReport(jp, false);

                        } catch (JRException e) {
                            System.out.println(e);
                        }
                        return true;
                    }
                };
            }
        };

        return service;
    }

    public Service<Boolean> load_cs_report(String sql, Connection conn, HashMap reportHash) {
        Service<Boolean> service;
        service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {

                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            updateMessage("Please wait while we generate report");
                            InputStream is = report_services.class.getResourceAsStream("/com/reports/myClientSection.jrxml");
                            JasperDesign jd = JRXmlLoader.load(is);

                            JRDesignQuery newQuery = new JRDesignQuery();
                            newQuery.setText(sql);//"Select * from livewell.account_patientsection" sql

                            jd.setQuery(newQuery);

                            JasperReport jr = JasperCompileManager.compileReport(jd);
                            JasperPrint jp = JasperFillManager.fillReport(jr, reportHash, conn);
                            JasperViewer.viewReport(jp, false);

                        } catch (JRException e) {
                            System.out.println(e);
                        }
                        return true;
                    }
                };
            }
        };

        return service;
    }

    public Service<Boolean> load_lab_report(String sql, Connection conn, String reporter) {
        Service<Boolean> service;
        service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {

                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            updateMessage("Please wait while we generate report");
                            InputStream is = report_services.class.getResourceAsStream("/com/reports/" + reporter + ".jrxml");
                            JasperDesign jd = JRXmlLoader.load(is);

                            JRDesignQuery newQuery = new JRDesignQuery();
                            newQuery.setText(sql);//"Select * from livewell.account_patientsection" sql

                            jd.setQuery(newQuery);

                            JasperReport jr = JasperCompileManager.compileReport(jd);
                            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
                            JasperViewer.viewReport(jp, false);

                        } catch (JRException e) {
                            System.out.println(e);
                        }
                        return true;
                    }
                };
            }
        };

        return service;
    }

    public class doctorS_report {

        public Service<Boolean> patient_report_init(String sql, Connection conn, HashMap reportHash) {
            Service<Boolean> service;
            service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            try {
                                updateMessage("Please wait while we generate report");
                                InputStream is = report_services.class.getResourceAsStream("/com/reports/complete_health_report.jrxml");
                                JasperDesign jd = JRXmlLoader.load(is);

                                JRDesignQuery newQuery = new JRDesignQuery();
                                newQuery.setText(sql);//"Select * from livewell.account_patientsection" sql

                                jd.setQuery(newQuery);

                                JasperReport jr = JasperCompileManager.compileReport(jd);
                                JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
                                JasperViewer.viewReport(jp, false);

                            } catch (JRException e) {
                                System.out.println(e);
                            }
                            return true;
                        }
                    };
                }
            };
            return service;
        }

    }

}
