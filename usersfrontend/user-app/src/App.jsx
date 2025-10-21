
import './App.css'
import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom';

import AppContent from './components/apps/AppContent';

function App() {


  return (
    <>
     <BrowserRouter>
      <AppContent/>
      </BrowserRouter>
    </>
  )
}

export default App
