let deferredPrompt;

window.addEventListener('load', (e) => {
    window.addEventListener('beforeinstallprompt', (e) => {
        // Prevent Chrome 67 and earlier from automatically showing the prompt
        e.preventDefault();
        // Stash the event so it can be triggered later.
        deferredPrompt = e;
        // Update UI notify the user they can add to home screen
        showInstallAppButton()
    });
});

function showInstallAppButton() {
    let installButton = document.querySelector(".install-prompt");
    installButton.style.display = "block";
    installButton.addEventListener("click", installApp);
}

function installApp() {
    // hide our user interface that shows our Install button
    let installButton = document.querySelector(".install-prompt");
    installButton.style.display = 'none';
    // Show the prompt
    deferredPrompt.prompt();
    // Wait for the user to respond to the prompt
    deferredPrompt.userChoice
        .then((choiceResult) => {
            // if (choiceResult.outcome === 'accepted') {
            //     console.log('User accepted the Install prompt');
            // } else {
            //     console.log('User dismissed the Install prompt');
            // }
            deferredPrompt = null;
        });
}
