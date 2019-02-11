using System;
using System.Collections.Generic;
using Microcharts;
using SkiaSharp;
using Entry = Microcharts.Entry;



using Xamarin.Forms;

namespace ClimbHard {
    public partial class AssessmentProgressPage : ContentPage {

        List<Entry> entries = new List<Entry> {
            new Entry(200) {
                Color = SKColor.Parse("#FF1493"),
                Label = "January",
                ValueLabel = "200"
            },
            new Entry(400){
                Label = "February",
                ValueLabel = "400",
                Color = SKColor.Parse("#68B9C0")
            },
            new Entry(-100)
            {
                Label = "March",
                ValueLabel = "-100",
                Color = SKColor.Parse("#90D585")
            }
        };



        public AssessmentProgressPage() {
            if (Device.RuntimePlatform == Device.iOS) Padding = new Thickness(0, 30, 0, 0);
            InitializeComponent();
            chartView.Chart = new RadialGaugeChart { Entries = entries };
        }

        public void PageBack(object sender, EventArgs e) {
            Navigation.PopModalAsync();
        }


    }
}
