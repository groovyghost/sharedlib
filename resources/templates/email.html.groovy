<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pipeline Notification</title>
    <link rel="stylesheet" href="./email.css">
</head>

<body>
    <div class="container">
        <div class="icon">
            <img id="icon" src="./jenkins.svg" alt="Jenkins Icon">
        </div>
        <div id="header" class="header SUCCESS">
            <span id="header-text"></span>
        </div>
        <div class="content">
            <table>
                <tr>
                    <td class="label">Project</td>
                    <td>${PROJECT_NAME}</td>
                </tr>
                <tr>
                    <td class="label">Branch</td>
                    <td>${BRANCH_NAME}</td>
                </tr>
                <tr>
                    <td class="label">Commit</td>
                    <td>${COMMIT_NAME}</td>
                </tr>
                <tr>
                    <td class="label">Commit Author</td>
                    <td>${COMMIT_AUTHOR}</td>
                </tr>
            </table>
            <div class="pipeline-url">
                <a href="${PIPELINE_URL}">Pipeline #${BUILD_NUMBER}</a>
            </div>
            <div class="pipeline-footer" id="pipeline-footer">
                Pipeline #${BUILD_NUMBER} triggered by ${COMMIT_AUTHOR}.
            </div>
        </div>
        <div class="footer">
            <p>Jenkins</p>
        </div>
    </div>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var pipelineStatus = '${PIPELINE_STATUS}';
            var headerElement = document.getElementById('header');
            var iconImg = document.getElementById('icon');
            var headerTextElement = document.getElementById('header-text');
            var pipelineUrlElement = document.getElementById('pipeline-footer');

            if (pipelineStatus === 'SUCCESS') {
                headerElement.classList.remove('FAILURE');
                headerElement.classList.add('SUCCESS');
                headerTextElement.textContent = '✔ Your pipeline has succeeded!';
                iconImg.src = './jenkins-success.svg';
                pipelineUrlElement.innerHTML = 'Pipeline #${BUILD_NUMBER} triggered by ${COMMIT_AUTHOR} has been Succeeded.';
            } else {
                headerElement.classList.remove('SUCCESS');
                headerElement.classList.add('FAILURE');
                headerTextElement.textContent = '✖ Your pipeline has failed!';
                iconImg.src = './jenkins-failed.svg';
                pipelineUrlElement.innerHTML = 'Pipeline #${BUILD_NUMBER} triggered by ${COMMIT_AUTHOR} has Failed.';
            }
        });
    </script>
</body>

</html>