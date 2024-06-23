<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pipeline Notification</title>
    <style>
        body {
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-family: sans-serif;
        }

        .container {
            background-color: white;
            width: 600px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            text-align: center;
            font-family: sans-serif;
        }

        .icon {
            padding: 20px;
            border-bottom: 1px solid #ddd;
        }

        .icon img {
            width: auto;
            height: auto;
        }

        .header {
            padding: 20px;
            font-size: 18px;
            border-bottom: 1px solid #ddd;
        }

        .header.SUCCESS {
            background-color: #28a745;
            color: white;
        }

        .header.FAILURE {
            background-color: #dc3545;
            color: white;
        }

        .content {
            padding: 20px;
            text-align: left;
            font-family: sans-serif;
        }

        .content table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .content table td {
            padding: 10px;
            border-bottom: 1px solid #f5f5f5;
            font-family: sans-serif;
        }

        .content table .label {
            font-weight: bold;
            width: 30%;
            color: #333;
        }

        .content table td.bold-text {
            font-weight: bold;
        }

        .pipeline-url {
            text-align: center;
            margin-bottom: 10px;
            font-family: sans-serif;
        }

        .pipeline-footer {
            font-family: sans-serif;
            text-align: center;
        }

        .pipeline-url a {
            color: #007bff;
            text-decoration: none;
        }

        .footer {
            text-align: center;
            padding: 10px 20px;
            color: #666;
            font-size: 12px;
            background-color: #f5f5f5;
            border-top: 1px solid #ddd;
            font-family: sans-serif;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="icon">
            <img id="icon" src="https://www.jenkins.io/images/logos/jenkins/jenkins.svg" alt="Jenkins Icon">
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
                iconImg.src = 'https://i.postimg.cc/SYNtdkFB/jenkins-success.png';
                pipelineUrlElement.innerHTML = 'Pipeline #${BUILD_NUMBER} triggered by ${COMMIT_AUTHOR} has been Succeeded.';
            } else {
                headerElement.classList.remove('SUCCESS');
                headerElement.classList.add('FAILURE');
                headerTextElement.textContent = '✖ Your pipeline has failed!';
                iconImg.src = 'https://i.postimg.cc/PpMR1Ncb/jenkins-failed.png';
                pipelineUrlElement.innerHTML = 'Pipeline #${BUILD_NUMBER} triggered by ${COMMIT_AUTHOR} has Failed.';
            }
        });
    </script>
</body>

</html>