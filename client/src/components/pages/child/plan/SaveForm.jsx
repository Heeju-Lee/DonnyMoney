import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate} from 'react-router-dom';

const SaveForm = () => {
    const [plan, setPlan] = useState({
        shopping : '',
        transport : '',
        cvs: '',
        food: '',
        others: '',
        saving: '',
        date : new Date().toISOString().split('T')[0],
        // childNum : 1
    })

    const changeForm=(e)=>{
        setPlan({
            ...plan,
            [e.target.name]:e.target.value
        })
    }

    const navigate = useNavigate();

    const submitPlan=(e)=>{
        e.preventDefault();

        axios ({
            url:'http://localhost:9999/plans/1',
            method: 'post',
            data: plan,
        })
        .then((res)=>{
            console.log(res.data);
            if(res.status===200){
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
                <div className='calculator'>
                    <h5>0원</h5>
                </div>
                <Form onSubmit={submitPlan} style={{width: "300px", margin:"0 auto"}}>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>쇼핑</Form.Label>
                        <Form.Control type="text" name="shopping" onChange={changeForm} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>교통</Form.Label>
                        <Form.Control type="text" name="transport" onChange={changeForm} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>편의점</Form.Label>
                        <Form.Control type="text" name="cvs" onChange={changeForm} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>먹을거</Form.Label>
                        <Form.Control type="text" name="food" onChange={changeForm} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>기타</Form.Label>
                        <Form.Control type="text" name="others" onChange={changeForm} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Label>저축</Form.Label>
                        <Form.Control type="text" name="saving" onChange={changeForm} />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        부모님께 보내기
                    </Button>
                </Form>
        </div>
    );
};

export default SaveForm;