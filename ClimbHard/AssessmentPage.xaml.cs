using System;
using System.Reflection;
using SQLite;
using System.Collections.Generic;

using Xamarin.Forms;

namespace ClimbHard {
    public partial class AssessmentPage : ContentPage {

        Assessment currentAssessment;


        // Default constructor, starts a new assessment object
        public AssessmentPage() {
            if (Device.RuntimePlatform == Device.iOS) Padding = new Thickness(0, 30, 0, 0);
            InitializeComponent();
            currentAssessment = new Assessment();
        }

        // submits this assessment object to something that will contain it
        public void SubmitAssessment(object sender, EventArgs e) {
            // if the assessment isn't finished
            //if (AssessmentIsFilled(currentAssessment) == false) { 
            //    DisplayAlert("Hold On", "Not all the assessment fields are filled", "OK");
            //    return;
            //}

            //Submit the current assessment to the sqlite database
            App.database.SaveAssessmentAsync(currentAssessment);

            Console.WriteLine("Assessment saved into the SQLite database");

            App.database.GetMaxHangsAsync();
            Navigation.PushModalAsync(new AssessmentProgressPage());
            //TODO move to a new page to display progress and results


        }

        void Handle_Height(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(height.Value, 0);
            heightValue.Text = value.ToString() + " inches";
            currentAssessment.Height = (value);
        }

        // handle the weight input
        void Handle_Weight(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(weight.Value, 0);
            weightValue.Text = value.ToString() + " lbs";
            currentAssessment.Weight = (value);
        }

        // handle the two-armed max hang value input
        void Handle_MaxHang(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(max.Value, 0);
            maxValue.Text = value.ToString() + " lbs";
            currentAssessment.MaxHang = (value);
            heavy.Text = "85% of max : " + (value * 0.85).ToString() + " lbs";
            moderate.Text = "75% of max : " + (value * 0.75).ToString() + " lbs";
            light.Text = "65% of max : " + (value * 0.65).ToString() + " lbs";
        }

        // handle max pullup
        void Handle_Max_PullUp(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(maxPull.Value, 0);
            pullupValue.Text = value.ToString() + " lbs";
            currentAssessment.MaxPullUp = (value);
        }

        // Handle the number of reps on the first set of the curve input
        void Handle_FirstSet(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(firstSet.Value, 0);
            firstSetValue.Text = value.ToString() + " reps";
            currentAssessment.FirstSetReps = (value);
        }

        // Handle the number of reps on the second set of the curve input
        void Handle_SecondSet(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(secondSet.Value, 0);
            secondSetValue.Text = value.ToString() + " reps";
            currentAssessment.SecondSetReps = (value);
        }

        // Handle the number of reps on the third set of the curve input
        void Handle_ThirdSet(object sender, Xamarin.Forms.ValueChangedEventArgs e) {
            double value = Math.Round(thirdSet.Value, 0);
            third.Text = value.ToString() + " reps";
            currentAssessment.ThirdSetReps = (value);
        }

        // return to home page
        public void ReturnHome(object sender, EventArgs e) {
            Navigation.PopModalAsync();
        }

        // go the the summary page for the whole assessment
        public void AssessmentSummary(object sender, EventArgs e) {
            Navigation.PushModalAsync(new AssessmentSummaryPage());
        }

        public void StrengthSummary(object sender, EventArgs e) {
            Navigation.PushModalAsync(new StrengthAssessmentSummary());
        }

        public void EnduranceSummary(object sender, EventArgs e) {
            Navigation.PushModalAsync(new EnduranceAssessmentSummary());
        }


        //////// Method checking to make sure that the assessment is completed
        public bool AssessmentIsFilled(object myObject) {
            foreach (PropertyInfo pi in myObject.GetType().GetProperties()) {
                if (pi.PropertyType == typeof(double)) {
                    double value = (double)pi.GetValue(myObject);
                    if (value.Equals(0)) return false;
                }
            }
            return true;
        }



    }
}
