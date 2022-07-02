'use strict';

(function () {

    let questions = null;
    let result = null;
    let id = null;

    function status(response) {
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(new Error(response.status));
        }
    }


    function toHtml(json){
        let str = '';
        str+=`<div class="container">`
        json.questionStack.forEach(obj=> {
            str+=`<div>${obj.question} </div>`;
            str+= `<div>${obj.answersNumber} answers</div>`;
            str+=`<form action="/AddAnswerServlet" method="get">
                        <input type="hidden" name="questionNumber" value="${obj.key}">
                        <input type="submit" value="answer">
                   </form>`;
            str+=`<button value="${obj.key}" data-question="${obj.question}">show answer</button>`;
            str+=`<ol id="${obj.key}"></ol>`;
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

        fetchLandingPage();


        document.addEventListener('click', function (e) { // button click listener

            if(e.target.tagName === "BUTTON") { // only if target is button

                if (e.target.outerText == "answer") {
                    localStorage.setItem('greeting', e.target.dataset.question);
                    fetch("/QuestionsServlet",
                        {
                            method: "POST",
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                                'datatype': 'json'
                            },
                            body: new URLSearchParams(e.target.value).toString()
                        })
                        .then(status)
                        .then(res => res.json())
                        .then(json => {
                            result = document.getElementById(e.target.value);
                            result.innerHTML = ""
                            result.style.display = 'block';
                            json.answers.forEach(obj => {
                                let li = document.createElement("li");
                                li.innerHTML = "[" + obj.username + "]" + obj.answer
                                result.append(li);
                            })
                            let button = document.createElement('button');
                            button.innerText = "hide";
                            button.value = e.target.value;
                            button.addEventListener("click", event=>{
                                if (event.target.outerText == "hide"){
                                    result = document.getElementById(event.target.value);
                                    result.style.display = "none";
                                    event.target.replaceWith(e.target);
                                }
                            });
                            e.target.replaceWith(button);
                        })

                }
            }

        }, false);


        })


})();