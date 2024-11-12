import React, { useState, useEffect } from 'react';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateForm = () => {
    const navigate = useNavigate();

    const {id} = useParams();
    const {pid} = useParams();

    const [plan, setPlan] = useState({
        shopping : '',
        transport : '',
        cvs: '',
        food: '',
        others: '',
        saving: '',
        date : new Date().toISOString().split('T')[0],
        // childNum : 1
    });

    
    useEffect(()=>{
        axios({
            url:'http://localhost:9999/plans/1',
            method: 'get',
        })
        .then((res)=>{
            console.log(res.data);
            setPlan(res.data);
        })
    },[])

    const changeForm=(e)=>{
        setPlan({
            ...plan,
            [e.target.name]:e.target.value
        })
    }

    const submitPlan=(e)=>{
        e.preventDefault();

        axios ({
            url:'http://localhost:9999/plans/1/1',
            method: 'put',
            data: plan,
        })
        .then((res)=>{
            console.log(res.data);
            if(res.status===202){
                alert("부모님께 소비내역 전달 완료~!!")
                navigate("/planPage");
            }
        }).catch((err)=>{
            console.log((err));
            alert("오류가 발생했습니다. 다시 시도해 주세요.");
        })
    }

    return (
        <div>
            <h3>카테고리</h3>
            <br/>
            <hr/>
            <Form onSubmit={submitPlan} style={{width: "300px", margin:"0 auto"}}>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>쇼핑</Form.Label>
                    <Form.Control type="text" name="shopping" placeholder={plan.shopping} onChange={changeForm}/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>교통</Form.Label>
                    <Form.Control type="text" name="transport" placeholder={plan.transport} onChange={changeForm} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>편의점</Form.Label>
                    <Form.Control type="text" name="cvs" placeholder={plan.cvs} onChange={changeForm} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>먹을거</Form.Label>
                    <Form.Control type="text" name="food" placeholder={plan.food} onChange={changeForm} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>기타</Form.Label>
                    <Form.Control type="text" name="others" placeholder={plan.others} onChange={changeForm} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                    <Form.Label>저축</Form.Label>
                    <Form.Control type="text" name="saving" placeholder={plan.saving} onChange={changeForm} />
                </Form.Group>
                <Button variant="primary" type='submit' onChange={changeForm}>부모님께 보내기</Button>
                </Form>
        </div>
    );
};

export default UpdateForm;