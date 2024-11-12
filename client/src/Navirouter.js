import React from "react";
import { Route, Routes } from "react-router-dom";
import HomePage from "./pages/common/HomePage";
import SaveForm from "./components/pages/child/plan/SaveForm";
import UpdateForm from "./components/pages/child/plan/UpdateForm";
import PlanPage from "./pages/child/PlanPage";

const Navirouter = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/save-form" element={<SaveForm />} />
        <Route path="/update-form" element={<UpdateForm />} />
        <Route path="/plan-page" element={<PlanPage />} />
      </Routes>
    </div>
  );
};

export default Navirouter;
