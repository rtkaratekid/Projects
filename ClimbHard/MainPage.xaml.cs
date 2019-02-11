using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace ClimbHard{
    public partial class MainPage : ContentPage{
        public MainPage(){
            if (Device.RuntimePlatform == Device.iOS) Padding = new Thickness(0, 30, 0, 0);
            InitializeComponent();
        }

        public void ToAssessment(object sender, EventArgs e){
            Navigation.PushModalAsync(new AssessmentPage());
        }

        public void ToLibrary(object sender, EventArgs e) {
            //TODO
        }

        public void ToLogs(object sender, EventArgs e) {
            //TODO
        }

        public void ToOptions(object sender, EventArgs e) {
            //TODO
        }
    }
}
