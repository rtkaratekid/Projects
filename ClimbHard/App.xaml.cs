using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation(XamlCompilationOptions.Compile)]
namespace ClimbHard {
    public partial class App : Application {

        public static AssessmentDatabase database = new AssessmentDatabase(
            (Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData) + "Assessment.db3"));

        public App() {
            InitializeComponent();

            MainPage = new MainPage();
        }

        protected override void OnStart() {
            // Handle when your app starts
        }

        protected override void OnSleep() {
            // Handle when your app sleeps
        }

        protected override void OnResume() {
            // Handle when your app resumes
        }
    }
}
