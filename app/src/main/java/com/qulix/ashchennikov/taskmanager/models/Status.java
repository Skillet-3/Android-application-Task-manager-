package com.qulix.ashchennikov.taskmanager.models;

/**
 *  Статус (Не начата | В процессе | Завершена | Отложена)
 */
public enum Status {

    /**
     * Не начата
     */
    NOT_STARTED ,

    /**
     * В процессе
     */
    IN_PROCESS ,

    /**
     * Завершена
     */
    COMPLETED ,

    /**
     * Отложена
     */
    POSTPONED
}
