package com.globomed.learn

import com.globomed.learn.GloboMedDbContract.EmployeeEntry

object DataManager {

    fun fetchAllEmployees(databaseHelper: DatabaseHelper) : ArrayList<Employee> {

        val employees = ArrayList<Employee>()

        val db = databaseHelper.readableDatabase

        val columns = arrayOf(
            EmployeeEntry.COLUMN_ID,
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION
        )

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null)

        val idPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ID)
        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)

        while (cursor.moveToNext()) {

            val id = cursor.getString(idPos)
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)

            employees.add(Employee(id, name, dob, designation))
        }

        cursor.close()
        return employees
    }

    fun fetchEmployee(databaseHelper: DatabaseHelper, empId: String) : Employee? {

        val db = databaseHelper.readableDatabase
        var employee: Employee? = null

        val columns = arrayOf(
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION
        )

        val selection = EmployeeEntry.COLUMN_ID + " LIKE ? "

        val selectionArgs = arrayOf(empId)

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)

        while (cursor.moveToNext()) {
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)

            employee = Employee(empId, name, dob, designation)
        }

        cursor.close()
        return employee

    }
}