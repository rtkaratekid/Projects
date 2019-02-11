using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using SQLite;

namespace ClimbHard {

    public class AssessmentDatabase {

        //sqlite database connection variable
        readonly SQLiteAsyncConnection database;

        //constructor for the database
        public AssessmentDatabase(string dbpath) {

            database = new SQLiteAsyncConnection(dbpath);

            // we want it to have the columns that are contained
            // in the assessment class
            database.CreateTableAsync<Assessment>().Wait();
        }

        // I think what this does is returns all the items in the table
        public Task<List<Assessment>> GetItemsAsync() => database.Table<Assessment>().ToListAsync();

        // saving an assessment object into the sqlite database
        public Task<int> SaveAssessmentAsync(Assessment assessment) => database.InsertAsync(assessment);

        private static void Print(Assessment s) {
            Console.WriteLine(s.MaxHang);
        }

        // hopefully getting all the max hang values into a list
        public Task<List<Assessment>> GetMaxHangsAsync() {
            //todo figure out how to get data out of this task thing

            //get a list of all the rows in the MaxHang Column
            Task<List<Assessment>> taskList = database.QueryAsync<Assessment>("SELECT [MaxHang] FROM [Assessment]");

            // maybe this will write out my max hangs?
            Console.WriteLine("--------------------\n");
            Console.WriteLine(taskList.Result);
            Console.WriteLine("Count " + taskList.Result.Count);
            taskList.Result.ForEach(Print);
            Console.WriteLine("--------------------\n");

            //Console.WriteLine();
            Console.WriteLine("Got Max Hangs");
            return taskList;
        }

        void HandleAction(Assessment obj) {
            Console.WriteLine(obj.MaxHang);
        }



        // hopefully clears the entire table
        public void ClearAsync() => database.ExecuteAsync("DELETE FROM Assessment");

        //TODO make getters for necessary variables
        // height, weight, gender, pullup, firstset, secondset, thirdset
        // start with just the hang, and then add implementation for others
        // when that is finished
    }
}