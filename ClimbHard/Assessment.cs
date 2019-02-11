using System;
using SQLite;

/*
    A class to create an object that stores all the relevant data for a climber's assessment
    This class assumes the assessment is done on a hangboard, in the future if other implements are used, 
    an option must be provided for those implements
 */

namespace ClimbHard {
    public class Assessment {

        //hoping that this is the right thing to do to set up sqlite database
        [PrimaryKey, AutoIncrement]
        public int ID { get; set; }
        // default to today's date
        public DateTime AssessmentDay { get; }

        public double Height { get; set; }

        public double Weight { get; set; }

        public double MaxPullUp { get; set; }

        public double MaxHang { get; set; }

        public double FirstSetReps { get; set; }

        public double SecondSetReps { get; set; }

        public double ThirdSetReps { get; set; }

        public Assessment() {
            AssessmentDay = DateTime.Now;
            Height = 0;
            Weight = 0;
            MaxHang = 0;
            FirstSetReps = 0;
            SecondSetReps = 0;
            ThirdSetReps = 0;
        }


    }
}
