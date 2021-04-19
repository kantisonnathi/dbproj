package com.dbproj.hms.repository;

import com.dbproj.hms.model.Appointment;
import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class AppointmentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Appointment save(Appointment appointment) {
        /*String query = "insert into appointment (docid, patientid, slot, complaints, diagnosis, appointment_date)" +
                " values (" + appointment.getDocID() + "," + appointment.getPatientID() + ", " + appointment.getSlot()
                 + ",'" + appointment.getComplaint() + "','" + appointment.getDiagnosis() + "', " + appointment.getDate()+")";
        jdbcTemplate.update(query);*/

        String query = "insert into appointment (docid, patientid, slot, complaints, diagnosis, appointment_date,billed) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(query,appointment.getDocID(), appointment.getPatientID(), appointment.getSlot(), appointment.getComplaint(),
                appointment.getDiagnosis(), appointment.getDate(),appointment.getBilled());

        /*jdbcTemplate.update(query, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,appointment.getDocID());
                preparedStatement.setInt(2,appointment.getPatientID());
                preparedStatement.setInt(3,appointment.getSlot());
                preparedStatement.setString(4,appointment.getComplaint());
                preparedStatement.setString(5,appointment.getDiagnosis());
            }
        });*/

        query = "select * from appointment where docid=" + appointment.getDocID() + " and slot=" + appointment.getSlot();
        List<Appointment> list = jdbcTemplate.query(query, new AppointmentRowMapper());
        return list.get(list.size()-1);
    }

    public Appointment findById(Integer id) {
        String query = "select * from appointment where appointmentID=" + id;
        List<Appointment> list = jdbcTemplate.query(query, new AppointmentRowMapper());
        return list.get(list.size()-1);
    }

    public List<Appointment> findByDocId(Integer id) {
        String query = "select * from appointment where docid=" + id;
        List<Appointment> list = jdbcTemplate.query(query, new AppointmentRowMapper());
        return list;
    }

    public List<Appointment> findByPatientId(Integer id) {
        String query = "select * from appointment where patientid=" + id;
        List<Appointment> list = jdbcTemplate.query(query, new AppointmentRowMapper());
        return list;
    }

    public List<Appointment> findByDocAndDate(Doctor doctor, Date date) {
        String query = "select * from appointment where docid=" + doctor.getID() + " and " +
                "appointment_date='" + date + "'";
        return jdbcTemplate.query(query, new AppointmentRowMapper());
    }

    public List<Transaction> getprice(Integer patientID) {
        // select sum(d.visitation_fees) from doctor d, appointment a, employee e where e.empid=d.empid and d.docid=a.docid and a.billed=FALSE and a.patientid=patientID;
        String query="select a.appointmentID,e.EmpName,d.visitation_fees,a.appointment_date from employee e,appointment a,doctor d  where e.empid=d.empid and d.docid=a.docid and a.billed=FALSE and a.patientid="+patientID;
        return jdbcTemplate.query(query,new TransactionMapper());
    }

    public void updateappointment(Integer patientId) {
        String query="update appointment set billed=True where patientid="+patientId;
        jdbcTemplate.update(query);
    }

    public void delete(Appointment appointment) {
        String query = "delete from appointment where appointmentID=" + appointment.getID();
        jdbcTemplate.update(query);
    }

    public void update(Appointment appointment) {
        String query = "update appointment set slot=?, complaints=?, diagnosis=?, appointment_date=?, billed=? where appointmentID=?";
        jdbcTemplate.update(query, appointment.getSlot(), appointment.getComplaint(),
                appointment.getDiagnosis(), appointment.getDate(), appointment.getBilled(), appointment.getID());
    }

    public void addtransaction(Transaction transaction) {
        String query="insert into transaction(empid,patientid,totalcost,date_of_transaction) values('"+transaction.getEmpid()+"','"+transaction.getPatientid()+"','"+ transaction.getTotalcost()+"','"+ LocalDate.now() +"')";
        jdbcTemplate.update(query);
    }
}
