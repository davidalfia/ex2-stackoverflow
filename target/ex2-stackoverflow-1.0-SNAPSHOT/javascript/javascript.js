'use strict';

(function () {

    let questions = null;
    let submit = null;

    function status(response) {
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(new Error(response.status));
        }
    }


    function toHtml(json){

        console.log(json);

        let str = '';
        str+=`<div class="container">`
        json.questionStack.forEach(obj=> {
            str+=`<div>${obj.question} </div>`;
            str+= `<div>${obj.answersNumber} answers</div>`;
            str+=`<form action="/AddAnswerServlet" method="get">
                        <input type="hidden" name="questionNumber" value="${obj.key}">
                        <input type="submit" value="answer">
                   </form>`;
            str+=`<button value="${obj.key}"></button>`;

            str+=`<br/>`
        })
        str+=`</div>`
        return str;
    }


    function fetchLandingPage()
    {
        fetch('/QuestionsServlet')
            .then(status)
            .then(res=> res.json())
            .then(json=>{
                questions.innerHTML =  toHtml(json);
            })
            .catch(()=>{

            })
    }

    document.addEventListener("DOMContentLoaded",function (){


        questions = document.getElementById("questions");

        let questionObj = "";
        let questionNumber = "";

        fetchLandingPage();

        document.addEventListener('click', function (e) { // button click listener

            if(e.target.tagName === "BUTTON"){ // only if target is button
                questionObj = document.getElementById(e.target.id);
                questionNumber = "question" + String(e.target.id).substr(-1);

                if (questionObj.innerHTML === 'Show') { // in case of button is on Show state
                    clearHtml(questionNumber);
                    document.getElementById(questionNumber).style.display = 'none';
                    questionObj.innerHTML = 'Hide'
                } else {
                    buildHtml(questions.get(), questionNumber);
                    document.getElementById(questionNumber).style.display = 'block';
                    questionObj.innerHTML = 'Show'
                }
            }
        }, false);


        })


    function buildHtml(data, id){ // build the html data

        let div = "";
        let answer = "";
        for(let inx of data){
            if(id.substr(-1) === inx){ // if substring of id matches the index in json array
                answer = data[data.indexOf(inx)+1].replaceAll('.',"").replaceAll(':'," - Answer : ").replaceAll(','," .Name : ").replace('['," Name : ").replace(']','')
                // answer is the string that stores each answer for the given question and builds it into the list
                div = document.getElementById(id);

                div.appendChild(arrangeData(answer)); // builds the list inside the div
            }
        }
    }
    function clearHtml(id){ // clear all created html data to avoid duplicates
        let items ;
        items = document.getElementById(id);
        while (items.firstChild) {
            items.removeChild(items.lastChild); // remove each element separately
        }
    }

    function arrangeData(answer){ // arranges data into a list
        let ans = answer.split(".");
        let ul = document.createElement("ul");  // create unordered list element

        for(let j = 0; j < ans.length;j++)
        {
            let li = document.createElement("li"); // create line of list
            li.innerText = ans[j];
            ul.appendChild(li);
        }
        return ul;
    }
})();