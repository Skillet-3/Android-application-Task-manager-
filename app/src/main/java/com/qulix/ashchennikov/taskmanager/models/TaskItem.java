package com.qulix.ashchennikov.taskmanager.models;

import com.qulix.ashchennikov.taskmanager.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс служит для хранения объектов
 */
public class TaskItem implements Serializable {

    /**
     * id объекта
     */
    private int id;

    /**
     * Наименование
     */
    private String name;

    /**
     * Работа (количество времени необходимого для выполнения задачи, часы)
     */
    private int workTime;

    /**
     * Дата начала
     */
    private Date dateStart;

    /**
     * Дата окончания
     */
    private Date dateEnd;

    /**
     * Статус (Не начата | В процессе | Завершена | Отложена)
     */
    private Status status;

    /**
     * id иконки
     */
    private int idImg;

    public TaskItem() {
        this.name = "";
        this.id = 0;
        this.workTime = 0;
        this.status = Status.NOT_STARTED;
        Calendar calendar = Calendar.getInstance();
        this.dateEnd = calendar.getTime();
        this.dateStart = calendar.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusToInt() {
        return status.ordinal();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = Status.values()[status];
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }
}
