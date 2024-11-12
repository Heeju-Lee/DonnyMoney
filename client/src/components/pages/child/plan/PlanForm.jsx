import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const PlanForm = (props) => {
  const navigate = useNavigate();

  const { id } = useParams();
  const { pid } = useParams();

  const [plan, setPlan] = useState({
    shopping: "",
    transport: "",
    cvs: "",
    food: "",
    others: "",
    saving: "",
    date: new Date().toISOString().split("T")[0],
    // childNum : 1
  });

  useEffect(() => {
    axios({
      url: "http://localhost:9999/plans/1",
      method: "get",
    }).then((res) => {
      console.log(res.data);
      setPlan(res.data);
    });
  }, []);

  const updatePlan = () => {
    alert("이동....");
    navigate("/updateForm/");
  };
  return (
    <div>
      <h3>카테고리</h3>
      <br />
      <hr />
      <Form style={{ width: "300px", margin: "0 auto" }}>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>쇼핑</Form.Label>
          <Form.Control
            type="text"
            name="shopping"
            readOnly
            value={plan.shopping}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>교통</Form.Label>
          <Form.Control
            type="text"
            name="transport"
            readOnly
            value={plan.transport}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>편의점</Form.Label>
          <Form.Control type="text" name="cvs" readOnly value={plan.cvs} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>먹을거</Form.Label>
          <Form.Control type="text" name="food" readOnly value={plan.food} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>기타</Form.Label>
          <Form.Control
            type="text"
            name="others"
            readOnly
            value={plan.others}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>저축</Form.Label>
          <Form.Control
            type="text"
            name="saving"
            readOnly
            value={plan.saving}
          />
        </Form.Group>
        <Button variant="warning" onClick={updatePlan}>
          수정
        </Button>
        {"     "}
        <Button variant="secondary" disabled>
          부모님께 보내기
        </Button>
      </Form>
    </div>
  );
};

export default PlanForm;
