﻿using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace ClimbHard {
    public partial class StrengthAssessmentSummary : ContentPage {
        public StrengthAssessmentSummary() {
            if (Device.RuntimePlatform == Device.iOS) Padding = new Thickness(0, 30, 0, 0);
            InitializeComponent();
        }

        void Handle_Clicked(object sender, System.EventArgs e) {
            Navigation.PopModalAsync();
        }
    }
}
