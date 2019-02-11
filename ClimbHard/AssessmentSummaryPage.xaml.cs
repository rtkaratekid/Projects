using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace ClimbHard {
    public partial class AssessmentSummaryPage : ContentPage {
        public AssessmentSummaryPage() {
            if (Device.RuntimePlatform == Device.iOS) Padding = new Thickness(0, 30, 0, 0);
            InitializeComponent();
        }

        public void ReturnToAssessment(object sender, EventArgs e) {
            Navigation.PopModalAsync();
        }
    }
}
